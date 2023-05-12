package com.trendyol.uicomponents.timelineviewcompose.item

import androidx.compose.foundation.clickable
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.trendyol.uicomponents.timelineviewcompose.Line
import com.trendyol.uicomponents.timelineviewcompose.model.FakeTimelineItemProvider.provideTimelineImageItem
import com.trendyol.uicomponents.timelineviewcompose.model.LineLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.PointLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TextLayoutId
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineItem
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineOrientation
import com.trendyol.uicomponents.timelineviewcompose.model.getVerticalConstraintSet
import com.trendyol.uicomponents.timelineviewcompose.scaleAnimation

@Composable
fun VerticalTimelineImageItem(
    modifier: Modifier = Modifier,
    item: TimelineItem.Image,
    isLastItem: Boolean,
    itemIndex: Int,
    onClick: () -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier,
        constraintSet = getVerticalConstraintSet(item.contentMargin),
    ) {
        AsyncImage(
            model = item.imageConfig.imageUrl,
            placeholder = item.imageConfig.placeholder,
            contentScale = ContentScale.Crop,
            contentDescription = item.imageContentDescription,
            modifier = Modifier
                .size(item.imageConfig.size)
                .clickable(onClick = onClick)
                .scaleAnimation(item.imageConfig.animation)
                .layoutId(PointLayoutId)
        )
        Text(
            maxLines = 2,
            text = item.text,
            style = item.textStyle,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId(TextLayoutId)
        )
        if (!isLastItem) {
            Line(
                itemIndex = itemIndex,
                config = item.lineConfig,
                orientation = TimelineOrientation.VERTICAL,
                modifier = Modifier
                    .width(item.lineConfig.thickness)
                    .layoutId(LineLayoutId),
            )
        }
    }
}

@Preview
@Composable
private fun VerticalTimelineImageItemPreview() {
    VerticalTimelineImageItem(
        isLastItem = false,
        item = provideTimelineImageItem(),
        itemIndex = 0
    )
}