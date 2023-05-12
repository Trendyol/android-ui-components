package com.trendyol.uicomponents.samplecompose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trendyol.uicomponents.timelineviewcompose.model.ImageConfig
import com.trendyol.uicomponents.timelineviewcompose.model.LineAnimation
import com.trendyol.uicomponents.timelineviewcompose.model.LineConfig
import com.trendyol.uicomponents.timelineviewcompose.model.LineType
import com.trendyol.uicomponents.timelineviewcompose.model.PointAnimation
import com.trendyol.uicomponents.timelineviewcompose.model.PointConfig
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineItem

internal object TimelineViewItemProvider {

    internal fun createTimelineItems(
        lineAnimationEnabled: Boolean = false,
        pointAnimationEnabled: Boolean = false,
    ): List<TimelineItem> {
        //active
        val item1 = TimelineItem.Point(
            pointConfig = PointConfig(
                outSideColor = colorWhite,
                insideColor = colorGreen
            ),
            text = "İade Talebi",
            textStyle = getTextStyle(color = colorGreen),
            lineConfig = getLineConfig(
                color = if (lineAnimationEnabled) colorGray else colorGreen,
                lineAnimation = if (lineAnimationEnabled) LineAnimation(targetColor = colorGreen) else null
            )
        )

        //active
        val item2 = TimelineItem.Point(
            pointConfig = PointConfig(
                outSideColor = colorWhite,
                insideColor = colorGreen
            ),
            text = "İade Kargoya Verildi",
            textStyle = getTextStyle(color = colorGreen),
            lineConfig = getLineConfig(
                color = if (lineAnimationEnabled) colorGray else colorGreen,
                lineAnimation = if (lineAnimationEnabled) LineAnimation(targetColor = colorGreen) else null
            )
        )

        //active
        val item3 = TimelineItem.Point(
            pointConfig = PointConfig(
                outSideColor = colorWhite,
                insideColor = colorGreen,
                animation = if (pointAnimationEnabled) PointAnimation(startDelay = 2000) else null
            ),
            text = "İade İnceleniyor",
            textStyle = getTextStyle(colorGreen),
            lineConfig = LineConfig(color = colorGray)
        )

        //passive
        val item4 = TimelineItem.Point(
            pointConfig = PointConfig(
                outSideColor = colorGray,
                insideColor = colorWhite
            ),
            text = "İade Edildi",
            textStyle = getTextStyle(colorGray),
            lineConfig = getLineConfig(colorGray)
        )
        return listOf(item1, item2, item3, item4)
    }

    internal fun createTimelineImageItems(
        lineAnimationEnabled: Boolean = false,
        pointAnimationEnabled: Boolean = false,
    ): List<TimelineItem.Image> {

        //active
        val item1 = TimelineItem.Image(
            imageConfig = getImageConfig(true),
            text = "İade Talebi",
            textStyle = getTextStyle(color = colorGreen),
            lineConfig = getLineConfig(
                color = if (lineAnimationEnabled) colorGray else colorGreen,
                lineAnimation = if (lineAnimationEnabled) LineAnimation(targetColor = colorGreen) else null
            )
        )

        val item2 = TimelineItem.Image(
            imageConfig = getImageConfig(true),
            text = "İade İnceleniyor",
            textStyle = getTextStyle(color = colorGreen),
            lineConfig = getLineConfig(
                color = if (lineAnimationEnabled) colorGray else colorGreen,
                lineAnimation = if (lineAnimationEnabled) LineAnimation(targetColor = colorGreen) else null
            )
        )

        val item3 = TimelineItem.Image(
            imageConfig = getImageConfig(isActive = true, animationEnabled = pointAnimationEnabled),
            text = "İade İnceleniyor",
            textStyle = getTextStyle(colorGreen),
            lineConfig = getLineConfig(colorGray),
        )

        //passive
        val item4 = TimelineItem.Image(
            imageConfig = getImageConfig(false),
            text = "İade Edildi",
            textStyle = getTextStyle(colorGray),
            lineConfig = getLineConfig(colorGray)
        )

        val item5 = TimelineItem.Image(
            imageConfig = getImageConfig(false),
            text = "İade İnceleniyor",
            textStyle = getTextStyle(colorGray),
            lineConfig = getLineConfig(colorGray)
        )

        val item6 = TimelineItem.Image(
            imageConfig = getImageConfig(false),
            text = "İade İnceleniyor",
            textStyle = getTextStyle(colorGray),
            lineConfig = getLineConfig(colorGray)
        )

        val item7 = TimelineItem.Image(
            text = "İade Edildi",
            textStyle = getTextStyle(colorGray),
            lineConfig = getLineConfig(colorGray),
            imageConfig = getImageConfig(false),
        )
        return listOf(item1, item2, item3, item4, item5, item6, item7)
    }
}

private fun getImageConfig(
    isActive: Boolean = false,
    animationEnabled: Boolean = false,
): ImageConfig {
    return ImageConfig(
        imageUrl = if (isActive) "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Sign-check-icon.png/800px-Sign-check-icon.png"
        else "https://icons.veryicon.com/png/o/miscellaneous/colorful-news-icon/wait-for-10.png",
        placeholder = null,
        animation = if (animationEnabled) PointAnimation(startDelay = 2000) else null
    )
}

private fun getLineConfig(
    color: Color,
    lineAnimation: LineAnimation? = null,
) = LineConfig(
    lineType = LineType.Solid,
    strokeCap = StrokeCap.Round,
    size = 56.dp,
    color = color,
    animation = lineAnimation
)

fun getTextStyle(color: Color): TextStyle {
    return TextStyle(
        color = color,
        fontFamily = FontFamily.Monospace,
        fontSize = 10.sp,
    )
}

internal val colorWhite = Color(0xFFFFFFFF)
internal val colorGray = Color(0xFFE5E5E5)
internal val colorGreen = Color(0xFF0BC15C)