/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trendyol.uicomponents.shimmercompose

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.OnGloballyPositionedModifier
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlin.math.max

@Stable
class PlaceholderState internal constructor(
    private val isVisible: State<Boolean>,
    private val maxScreenDimension: Float
) {
    suspend fun startShimmer() {
        coroutineScope {
            while (isActive) {
                withInfiniteAnimationFrameMillis {
                    frameMillis.value = it
                }
            }
        }
    }

    val placeholderProgression: Float by derivedStateOf {
        val absoluteProgression =
            (frameMillis.value.mod(PLACEHOLDER_GAP_BETWEEN_ANIMATION_LOOPS_MS).coerceAtMost(
                PLACEHOLDER_PROGRESSION_DURATION_MS
            ).toFloat() /
                    PLACEHOLDER_PROGRESSION_DURATION_MS)
        val progression = lerp(-maxScreenDimension, maxScreenDimension, absoluteProgression)
        progression
    }


    val isShowContent: Boolean by derivedStateOf {
        placeholderStage == PlaceholderStage.ShowContent
    }

    val isWipeOff: Boolean by derivedStateOf {
        placeholderStage == PlaceholderStage.WipeOff
    }

    internal val gradientWidth: Float by derivedStateOf {
        maxScreenDimension
    }

    internal var placeholderStage: PlaceholderStage =
        if (isVisible.value) PlaceholderStage.ShowPlaceholder
        else PlaceholderStage.ShowContent
        get() {
            if (field != PlaceholderStage.ShowContent) {
                if (startOfNextPlaceholderAnimation == 0L) {
                    startOfNextPlaceholderAnimation =
                        (frameMillis.value.div(PLACEHOLDER_GAP_BETWEEN_ANIMATION_LOOPS_MS) + 1) *
                                PLACEHOLDER_GAP_BETWEEN_ANIMATION_LOOPS_MS
                } else if (frameMillis.value >= startOfNextPlaceholderAnimation) {
                    field = checkForStageTransition(
                        field,
                        isVisible.value.not(),
                    )
                    startOfNextPlaceholderAnimation =
                        (frameMillis.value.div(PLACEHOLDER_GAP_BETWEEN_ANIMATION_LOOPS_MS) + 1) *
                                PLACEHOLDER_GAP_BETWEEN_ANIMATION_LOOPS_MS
                }
            }
            return field
        }

    private val frameMillis = mutableStateOf(0L)

    private var startOfNextPlaceholderAnimation = 0L
}

@Composable
fun rememberShimmerState(isVisible: Boolean): PlaceholderState {
    val maxScreenDimension = with(LocalDensity.current) {
        Dp(max(screenHeightDp(), screenWidthDp()).toFloat()).toPx()
    }
    val isShimmerVisibleState = rememberUpdatedState(isVisible)
    return remember {
        PlaceholderState(
            isShimmerVisibleState,
            maxScreenDimension
        )
    }
}

private fun checkForStageTransition(
    placeholderStage: PlaceholderStage,
    contentReady: Boolean
): PlaceholderStage {
    return if (placeholderStage == PlaceholderStage.ShowPlaceholder && contentReady) {
        PlaceholderStage.WipeOff
    } else if (placeholderStage == PlaceholderStage.WipeOff) {
        PlaceholderStage.ShowContent
    } else placeholderStage
}

fun Modifier.shimmer(
    isVisible: Boolean,
    isWipeOffAnimationEnabled: Boolean = true,
    shape: Shape? = null,
): Modifier = composed {

    val shimmerState = rememberShimmerState(isVisible)

    if (!shimmerState.isShowContent) {
        LaunchedEffect(shimmerState) {
            shimmerState.startShimmer()
        }
    }

    then(
        Modifier
            .placeholderShimmer(shimmerState, shape)
            .placeholder(shimmerState, isWipeOffAnimationEnabled, shape)
    )
}

fun Modifier.placeholder(
    placeholderState: PlaceholderState,
    isWipeOffAnimationEnabled: Boolean,
    shape: Shape? = null,
    color: Color? = null
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "placeholder"
        properties["placeholderState"] = placeholderState
        properties["shape"] = shape
        properties["color"] = color
    }
) {
    PlaceholderModifier(
        placeholderState = placeholderState,
        isWipeOffAnimationEnabled = isWipeOffAnimationEnabled,
        color = color ?: MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
            .compositeOver(MaterialTheme.colors.surface),
        shape = shape ?: MaterialTheme.shapes.small
    )
}

fun Modifier.placeholderShimmer(
    placeholderState: PlaceholderState,
    shape: Shape? = null,
    color: Color? = null
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "placeholderShimmer"
        properties["placeholderState"] = placeholderState
        properties["shape"] = shape
        properties["color"] = color
    }
) {
    PlaceholderShimmerModifier(
        placeholderState = placeholderState,
        color = color ?: MaterialTheme.colors.onSurface,
        shape = shape ?: MaterialTheme.shapes.small
    )
}

@Immutable
@JvmInline
internal value class PlaceholderStage internal constructor(internal val type: Int) {

    companion object {
        val ShowPlaceholder = PlaceholderStage(0)
        val WipeOff = PlaceholderStage(1)
        val ShowContent = PlaceholderStage(2)
    }

    override fun toString(): String {
        return when (this) {
            ShowPlaceholder -> "PlaceholderStage.ShowPlaceholder"
            WipeOff -> "PlaceholderStage.WipeOff"
            else -> "PlaceholderStage.ShowContent"
        }
    }
}

private fun wipeOffBrush(
    color: Color,
    offset: Offset,
    placeholderState: PlaceholderState
): Brush {
    return Brush.linearGradient(
        colors = listOf(
            Color.Transparent,
            color
        ),
        start = Offset(
            x = placeholderState.placeholderProgression - offset.x,
            y = placeholderState.placeholderProgression - offset.y
        ),
        end = Offset(
            x = placeholderState.placeholderProgression - offset.x + placeholderState.gradientWidth.div(
                1.4f
            ),
            y = placeholderState.placeholderProgression - offset.y + placeholderState.gradientWidth.div(
                1.4f
            )
        )
    )
}

private abstract class AbstractPlaceholderModifier(
    private val alpha: Float = 1.0f,
    private val shape: Shape
) : DrawModifier, OnGloballyPositionedModifier {

    private var offset by mutableStateOf(Offset.Zero)

    private var lastSize: Size? = null
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null

    override fun onGloballyPositioned(coordinates: LayoutCoordinates) {
        offset = coordinates.positionInRoot()
    }

    abstract fun generateBrush(offset: Offset): Brush?

    override fun ContentDrawScope.draw() {
        val brush = generateBrush(offset)

        drawContent()
        if (brush != null) {
            if (shape === RectangleShape) {
                drawRect(brush)
            } else {
                drawOutline(brush)
            }
        }
    }

    private fun ContentDrawScope.drawOutline(brush: Brush) {
        val outline =
            if (size == lastSize && layoutDirection == lastLayoutDirection) {
                lastOutline!!
            } else {
                shape.createOutline(size, layoutDirection, this)
            }
        drawOutline(outline, brush = brush, alpha = alpha)
        lastOutline = outline
        lastSize = size
    }
}

private class PlaceholderModifier constructor(
    private val placeholderState: PlaceholderState,
    private val isWipeOffAnimationEnabled: Boolean,
    private val color: Color,
    alpha: Float = 1.0f,
    val shape: Shape
) : AbstractPlaceholderModifier(alpha, shape) {
    override fun generateBrush(offset: Offset): Brush? {
        return when (placeholderState.placeholderStage) {
            PlaceholderStage.ShowPlaceholder -> {
                SolidColor(color)
            }

            PlaceholderStage.WipeOff -> {
                takeIf { isWipeOffAnimationEnabled }?.let {
                    wipeOffBrush(color, offset, placeholderState)
                }
            }

            else -> {
                null
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlaceholderModifier

        if (placeholderState != other.placeholderState) return false
        if (color != other.color) return false
        if (shape != other.shape) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeholderState.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }
}

private class PlaceholderShimmerModifier constructor(
    private val placeholderState: PlaceholderState,
    private val color: Color,
    alpha: Float = 1.0f,
    val shape: Shape
) : AbstractPlaceholderModifier(alpha, shape) {
    override fun generateBrush(offset: Offset): Brush? {
        return if (placeholderState.placeholderStage == PlaceholderStage.ShowPlaceholder) {
            Brush.linearGradient(
                start = Offset(
                    x = placeholderState.placeholderProgression - offset.x,
                    y = placeholderState.placeholderProgression - offset.y
                ),
                end = Offset(
                    x = placeholderState.placeholderProgression - offset.x +
                            placeholderState.gradientWidth,
                    y = placeholderState.placeholderProgression - offset.y +
                            placeholderState.gradientWidth
                ),
                colorStops = listOf(
                    0f to color.copy(alpha = 0f),
                    0.65f to color.copy(alpha = 0.13f),
                    1f to color.copy(alpha = 0f),
                ).toTypedArray()
            )
        } else {
            null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlaceholderShimmerModifier

        if (placeholderState != other.placeholderState) return false
        if (color != other.color) return false
        if (shape != other.shape) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeholderState.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + shape.hashCode()
        return result
    }
}

@Composable
internal fun screenHeightDp() = LocalContext.current.resources.configuration.screenHeightDp

@Composable
internal fun screenWidthDp() = LocalContext.current.resources.configuration.screenWidthDp

internal const val PLACEHOLDER_PROGRESSION_DURATION_MS = 800L
internal const val PLACEHOLDER_DELAY_BETWEEN_PROGRESSIONS_MS = 800L
internal const val PLACEHOLDER_GAP_BETWEEN_ANIMATION_LOOPS_MS =
    PLACEHOLDER_PROGRESSION_DURATION_MS + PLACEHOLDER_DELAY_BETWEEN_PROGRESSIONS_MS
