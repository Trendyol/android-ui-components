package com.trendyol.uicomponents.timelineviewcompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.timelineviewcompose.model.PointConfig

@Composable
internal fun TimelinePoint(
    modifier: Modifier = Modifier,
    config: PointConfig,
    onClick: () -> Unit = {},
) {

    Canvas(
        modifier = modifier
            .size(config.size + config.borderWidth)
            .graphicsLayer { alpha = 0.99f }
            .scaleAnimation(config.animation)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    ) {

        val radiusPx = config.size.div(2).toPx()
        val insidePointRadius = radiusPx - config.borderWidth.times(2).toPx()

        // inside
        drawCircle(
            color = config.insideColor,
            radius = insidePointRadius,
        )

        // outside
        drawCircle(
            color = config.outSideColor,
            radius = radiusPx,
            blendMode = BlendMode.DstOver
        )

        // shadow
        drawCircle(
            color = config.insideColor.copy(alpha = 0.2f),
            radius = radiusPx + config.borderWidth.div(2).toPx(),
            blendMode = BlendMode.DstOver,
        )
    }
}

@Preview
@Composable
private fun TimelinePointPreview() {
    TimelinePoint(
        config = PointConfig(
            size = 36.dp,
            borderWidth = 2.dp,
            insideColor = Color.Green,
            outSideColor = Color.White
        )
    )
}