package com.trendyol.uicomponents.timelineviewcompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object FakeTimelineItemProvider {

    internal fun provideTimelineItem(
        text: String = "Step Name",
        borderWidth: Dp = 2.dp,
        insideColor: Color = Color(0xFFF27A1A),
        lineColor: Color = Color(0xFF666666),
        lineWidth: Dp = 25.dp,
        outsideColor: Color = Color.LightGray,
        pointSize: Dp = 28.dp,
        textStyle: TextStyle = TextStyle(fontSize = 10.sp, fontFamily = FontFamily.Serif),
    ): TimelineItem.Point {
        return TimelineItem.Point(
            textStyle = textStyle,
            lineConfig = LineConfig(color = lineColor, size = lineWidth),
            text = text,
            pointConfig = PointConfig(
                borderWidth = borderWidth,
                insideColor = insideColor,
                outSideColor = outsideColor,
                size = pointSize,
            ),
        )
    }

    internal fun provideTimelineImageItem(): TimelineItem.Image {
        return TimelineItem.Image(
            text = "Text\nText",
            imageConfig = ImageConfig(
                size = 32.dp,
                imageUrl = ""
            ),
            textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center),
            lineConfig = LineConfig(
                size = 54.dp,
                color = Color.Red,
            ),
        )
    }

    internal fun provideTimelineItemWithText(
        text: String = "Step Name",
        borderWidth: Dp = 2.dp,
        insideColor: Color = Color(0xFFF27A1A),
        lineColor: Color = Color(0xFF666666),
        lineWidth: Dp = 25.dp,
        outsideColor: Color = Color.LightGray,
        pointSize: Dp = 28.dp,
        textStyle: TextStyle = TextStyle(fontSize = 10.sp, fontFamily = FontFamily.Serif),
    ): TimelineItem.PointWithIndex {
        return TimelineItem.PointWithIndex(
            textStyle = textStyle,
            lineConfig = LineConfig(color = lineColor, size = lineWidth),
            text = text,
            pointConfig = PointConfig(
                borderWidth = borderWidth,
                insideColor = insideColor,
                outSideColor = outsideColor,
                size = pointSize,
            ),
        )
    }

    internal fun provideTimelineItemList(): List<TimelineItem> {
        return (0..5).map { provideTimelineItem(text = "Step $it") }
    }

    internal fun provideTimelineItemWithTextList(): List<TimelineItem.PointWithIndex> {
        return (0..5).map { provideTimelineItemWithText(text = "Step $it") }
    }
}