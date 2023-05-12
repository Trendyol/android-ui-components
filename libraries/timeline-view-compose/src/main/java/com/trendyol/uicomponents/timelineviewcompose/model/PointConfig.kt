package com.trendyol.uicomponents.timelineviewcompose.model

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Immutable
data class PointConfig(
    val outSideColor: Color,
    val borderWidth: Dp = TimelineViewDefaults.BorderWidth,
    val insideColor: Color,
    val size: Dp = TimelineViewDefaults.ItemSize,
    val animation: PointAnimation? = null,
) {

    fun getSizeWithBorder(): Dp {
        return borderWidth + size
    }
}

@Immutable
data class PointAnimation(
    val initialValue: Float = 0.5f,
    val targetValue: Float = 1f,
    val startDelay: Int = 0,
    val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        repeatMode = RepeatMode.Reverse,
        animation = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        ),
    ),
)