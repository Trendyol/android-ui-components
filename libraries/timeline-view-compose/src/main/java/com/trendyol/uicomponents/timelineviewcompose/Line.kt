package com.trendyol.uicomponents.timelineviewcompose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.timelineviewcompose.model.LineConfig
import com.trendyol.uicomponents.timelineviewcompose.model.LineType
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineOrientation

@Composable
internal fun Line(
    config: LineConfig,
    itemIndex: Int,
    modifier: Modifier = Modifier,
    orientation: TimelineOrientation = TimelineOrientation.HORIZONTAL,
) {
    val pathEffect = when (config.lineType) {
        is LineType.Dashed -> PathEffect.dashPathEffect(
            config.lineType.intervals,
            config.lineType.phase
        )

        else -> null
    }

    val itemSize = when (orientation) {
        TimelineOrientation.HORIZONTAL -> DpSize(
            width = config.size,
            height = config.thickness
        )

        TimelineOrientation.VERTICAL -> DpSize(
            width = config.thickness,
            height = config.size
        )
    }

    val animateLine = remember { Animatable(config.animation?.initialValue ?: 0f) }
    if (config.animation != null) {
        LaunchedEffect(animateLine) {
            animateLine.animateTo(
                targetValue = config.animation.targetValue,
                animationSpec = tween(
                    easing = LinearEasing,
                    durationMillis = config.animation.durationMillis,
                    delayMillis = itemIndex * config.animation.durationMillis
                )
            )
        }
    }

    Canvas(
        modifier = modifier.size(itemSize.width, itemSize.height)
    ) {
        when (orientation) {
            TimelineOrientation.HORIZONTAL -> drawHorizontalLine(
                config,
                pathEffect,
                animateLine.value,
                config.animation?.targetColor,
            )

            TimelineOrientation.VERTICAL -> drawVerticalLine(
                config,
                pathEffect,
                animateLine.value,
                config.animation?.targetColor
            )
        }
    }
}

private fun DrawScope.drawHorizontalLine(
    lineConfig: LineConfig,
    pathEffect: PathEffect?,
    animatedColorProgress: Float = 0f,
    targetColor: Color?,
) {
    val centerYPos = size.height.div(2)
    drawLine(
        cap = lineConfig.strokeCap,
        start = Offset(0f, centerYPos),
        end = Offset(size.width, centerYPos),
        color = lineConfig.color,
        pathEffect = pathEffect,
        strokeWidth = size.height,
        blendMode = BlendMode.SrcOver
    )
    if (animatedColorProgress > 0f) {
        drawLine(
            cap = lineConfig.strokeCap,
            start = Offset(0f, centerYPos),
            end = Offset(size.width * animatedColorProgress, centerYPos),
            color = targetColor ?: Color.Unspecified,
            pathEffect = pathEffect,
            strokeWidth = size.height,
            blendMode = BlendMode.SrcOver
        )
    }
}

private fun DrawScope.drawVerticalLine(
    lineConfig: LineConfig,
    pathEffect: PathEffect?,
    animatedColorProgress: Float = 0f,
    targetColor: Color?,
) {
    val centerXPos = size.width.div(2)
    drawLine(
        blendMode = BlendMode.SrcOver,
        cap = lineConfig.strokeCap,
        color = lineConfig.color,
        end = Offset(centerXPos, size.height),
        pathEffect = pathEffect,
        start = Offset(centerXPos, 0f),
        strokeWidth = size.width,
    )
    if (animatedColorProgress > 0f) {
        drawLine(
            cap = lineConfig.strokeCap,
            blendMode = BlendMode.SrcOver,
            color = targetColor ?: Color.Unspecified,
            pathEffect = pathEffect,
            strokeWidth = size.width,
            start = Offset(centerXPos, 0f),
            end = Offset(centerXPos, size.height * animatedColorProgress),
        )
    }
}

//region previews
@Preview
@Composable
private fun TimelineLineItemHorizontalSolid() {
    Line(
        orientation = TimelineOrientation.HORIZONTAL,
        config = LineConfig(
            color = Color.Green,
            size = 54.dp,
            thickness = 2.dp,
            lineType = LineType.Solid
        ),
        itemIndex = 0
    )
}

@Preview
@Composable
private fun TimelineLineItemPreviewHorizontalDashed() {
    Line(
        orientation = TimelineOrientation.HORIZONTAL,
        config = LineConfig(
            color = Color.Green,
            size = 54.dp,
            thickness = 2.dp,
            lineType = LineType.Dashed()
        ),
        itemIndex = 0
    )
}

@Preview
@Composable
private fun TimelineLineItemPreviewVerticalSolid() {
    Line(
        orientation = TimelineOrientation.VERTICAL,
        config = LineConfig(
            color = Color.Green,
            size = 54.dp,
            thickness = 2.dp,
            lineType = LineType.Solid
        ),
        itemIndex = 0
    )
}

@Preview
@Composable
private fun TimelineLineItemPreviewVerticalDashed() {
    Line(
        orientation = TimelineOrientation.VERTICAL,
        config = LineConfig(
            color = Color.Green,
            size = 54.dp,
            thickness = 2.dp,
            lineType = LineType.Dashed()
        ),
        itemIndex = 0
    )
}
//endregion