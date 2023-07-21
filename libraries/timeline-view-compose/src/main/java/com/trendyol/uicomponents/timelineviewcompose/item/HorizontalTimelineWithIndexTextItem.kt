package com.trendyol.uicomponents.timelineviewcompose.item

import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.trendyol.uicomponents.timelineviewcompose.Line
import com.trendyol.uicomponents.timelineviewcompose.TimelinePoint
import com.trendyol.uicomponents.timelineviewcompose.model.FakeTimelineItemProvider
import com.trendyol.uicomponents.timelineviewcompose.model.IndexTextLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.LineLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.PointLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TextLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineItem
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineOrientation
import com.trendyol.uicomponents.timelineviewcompose.model.getHorizontalConstraintSet

@Composable
internal fun HorizontalTimelineWithIndexTextItemItem(
    modifier: Modifier = Modifier,
    item: TimelineItem.PointWithIndex,
    isLastItem: Boolean = false,
    itemIndex: Int,
    itemIndexTextStyle: TextStyle,
    customLineWidth : Dp = 0.dp,
    onClick: () -> Unit = {},
) {

    val itemWidth  =item.pointConfig.getSizeWithBorder() + customLineWidth

    ConstraintLayout(
        constraintSet = getHorizontalConstraintSet(item.contentMargin),
        modifier = modifier
            .width(customLineWidth)
    ) {
        TimelinePoint(
            config = item.pointConfig,
            modifier = Modifier.layoutId(PointLayoutId),
            onClick = onClick,
            pointShadowConfig = item.pointShadowConfig
        )
        Text(
            text = item.text,
            style = item.textStyle,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .width(itemWidth)
                .layoutId(TextLayoutId)
        )
        Text(
            text = (itemIndex + 1).toString(),
            modifier = Modifier.layoutId(IndexTextLayoutId),
            style = itemIndexTextStyle,
        )
        if (!isLastItem) {
            Line(
                itemIndex = itemIndex,
                config = item.lineConfig,
                orientation = TimelineOrientation.HORIZONTAL,
                modifier = Modifier.layoutId(LineLayoutId),
                customLineWidth = customLineWidth
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HorizontalWithIndexTimelineItemPreview() {
    HorizontalTimelineWithIndexTextItemItem(
        item = FakeTimelineItemProvider.provideTimelineItemWithText(text = "Siparişiniz hazırlanıyor"),
        isLastItem = false,
        itemIndex = 0,
        itemIndexTextStyle = TextStyle(color = Color.White),
    )
}