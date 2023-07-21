package com.trendyol.uicomponents.timelineviewcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.timelineviewcompose.item.HorizontalTimelineWithIndexTextItemItem
import com.trendyol.uicomponents.timelineviewcompose.model.FakeTimelineItemProvider
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineItem

/***
 * The line sizes are automatically calculated and set to fit the screen.
 * It accepts 'PointWithIndex' as an item.
 */
@Composable
fun HorizontalTimelineWithIndexTextView(
    items: List<TimelineItem.PointWithIndex>,
    modifier: Modifier = Modifier,
    onClick: (TimelineItem) -> Unit = {},
) {

    var availableWidth by remember { mutableStateOf(0) }
    val availableWidthPx = with(LocalDensity.current) { availableWidth.toDp() }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { availableWidth = it.size.width }) {
        items.forEachIndexed { index, item ->
            val lineWidthDp =
                ((availableWidthPx - (item.pointConfig.getSizeWithBorder() * items.size)) / (items.size - 1))
            val itemWidth = item.pointConfig.getSizeWithBorder() + lineWidthDp

            HorizontalTimelineWithIndexTextItemItem(
                item = item,
                itemIndex = index,
                isLastItem = items.isLastItem(index),
                onClick = { onClick(items[index]) },
                itemIndexTextStyle = item.indexTextStyle,
                customLineWidth = itemWidth
            )
        }
    }
}

private fun List<TimelineItem>.isLastItem(index: Int): Boolean {
    return index == size - 1
}

@Composable
@Preview(showBackground = true)
private fun TimelineViewVerticalPreview() {
    HorizontalTimelineWithIndexTextView(
        modifier = Modifier.padding(horizontal = 16.dp),
        items = FakeTimelineItemProvider.provideTimelineItemWithTextList()
    )
}