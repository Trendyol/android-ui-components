package com.trendyol.uicomponents.timelineviewcompose.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.trendyol.uicomponents.timelineviewcompose.Line
import com.trendyol.uicomponents.timelineviewcompose.model.FakeTimelineItemProvider.provideTimelineImageItem
import com.trendyol.uicomponents.timelineviewcompose.model.LineLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.PointLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TextLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineItem
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineOrientation
import com.trendyol.uicomponents.timelineviewcompose.model.getHorizontalConstraintSet
import com.trendyol.uicomponents.timelineviewcompose.scaleAnimation

@Composable
internal fun HorizontalTimelineImageItem(
    modifier: Modifier = Modifier,
    item: TimelineItem.Image,
    isLastItem: Boolean,
    isFirstItem: Boolean,
    itemIndex: Int,
    onClick: () -> Unit = {},
) {

    val itemWidth = if (isLastItem) {
        item.imageConfig.size + item.lineConfig.size.div(2)
    } else {
        item.imageConfig.size + item.lineConfig.size
    }

    val startPadding = if (isFirstItem) item.lineConfig.size.div(2) else 0.dp

    ConstraintLayout(
        constraintSet = getHorizontalConstraintSet(item.contentMargin),
        modifier = modifier
            .padding(start = startPadding)
            .width(itemWidth + item.imageConfig.borderWidth)
    ) {
        AsyncImage(
            model = item.imageConfig.imageUrl,
            contentDescription = item.imageContentDescription,
            contentScale = ContentScale.Fit,
            placeholder = item.imageConfig.placeholder,
            modifier = Modifier
                .size(item.imageConfig.borderWidth + item.imageConfig.size)
                .clickable(onClick = onClick)
                .scaleAnimation(item.imageConfig.animation)
                .layoutId(PointLayoutId)
        )
        Text(
            style = item.textStyle,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            text = item.text,
            maxLines = 2,
            modifier = Modifier
                .width(itemWidth)
                .layoutId(TextLayoutId)
        )
        if (!isLastItem) {
            Line(
                config = item.lineConfig,
                itemIndex = itemIndex,
                orientation = TimelineOrientation.HORIZONTAL,
                modifier = Modifier.layoutId(LineLayoutId),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalTimelineImageItemPreview() {
    HorizontalTimelineImageItem(
        isLastItem = false,
        isFirstItem = false,
        item = provideTimelineImageItem(),
        itemIndex = 0
    )
}