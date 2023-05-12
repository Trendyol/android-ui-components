package com.trendyol.uicomponents.timelineviewcompose.item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.trendyol.uicomponents.timelineviewcompose.Line
import com.trendyol.uicomponents.timelineviewcompose.TimelinePoint
import com.trendyol.uicomponents.timelineviewcompose.model.FakeTimelineItemProvider
import com.trendyol.uicomponents.timelineviewcompose.model.LineLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.PointLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TextLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineItem
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineOrientation
import com.trendyol.uicomponents.timelineviewcompose.model.getHorizontalConstraintSet

@Composable
internal fun HorizontalTimelineItem(
    modifier: Modifier = Modifier,
    item: TimelineItem.Point,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    itemIndex: Int,
    onClick: () -> Unit = {},
) {

    val itemWidth = if (isLastItem) {
        item.pointConfig.getSizeWithBorder() + item.lineConfig.size.div(2)
    } else {
        item.pointConfig.getSizeWithBorder() + item.lineConfig.size
    }

    val startPadding = if (isFirstItem) item.lineConfig.size.div(2) else 0.dp

    ConstraintLayout(
        constraintSet = getHorizontalConstraintSet(item.contentMargin),
        modifier = modifier
            .padding(start = startPadding)
            .width(itemWidth)
    ) {
        TimelinePoint(
            config = item.pointConfig,
            modifier = Modifier.layoutId(PointLayoutId),
            onClick = onClick,
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
        if (!isLastItem) {
            Line(
                itemIndex = itemIndex,
                config = item.lineConfig,
                orientation = TimelineOrientation.HORIZONTAL,
                modifier = Modifier.layoutId(LineLayoutId)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewHorizontalTimelineItemPreview() {
    HorizontalTimelineItem(
        item = FakeTimelineItemProvider.provideTimelineItem(text = "Siparişiniz hazırlanıyor."),
        isLastItem = false,
        isFirstItem = true,
        itemIndex = 0
    )
}