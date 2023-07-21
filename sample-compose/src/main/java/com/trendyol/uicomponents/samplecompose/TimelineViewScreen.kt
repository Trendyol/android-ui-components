package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trendyol.uicomponents.samplecompose.TimelineViewItemProvider.createHorizontalWithIndexTimelineItems
import com.trendyol.uicomponents.samplecompose.TimelineViewItemProvider.createTimelineImageItems
import com.trendyol.uicomponents.samplecompose.TimelineViewItemProvider.createTimelineItems
import com.trendyol.uicomponents.samplecompose.ui.theme.ColorBackground
import com.trendyol.uicomponents.samplecompose.ui.theme.ColorPrimary
import com.trendyol.uicomponents.timelineviewcompose.HorizontalTimelineWithIndexTextView
import com.trendyol.uicomponents.timelineviewcompose.TimelineView
import com.trendyol.uicomponents.timelineviewcompose.model.TimelineOrientation

@Composable
fun TimelineViewScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = ColorPrimary,
                elevation = 0.dp
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "TimelineView",
                    style = MaterialTheme.typography.h6.copy(color = Color.White)
                )
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(ColorBackground)
                .verticalScroll(rememberScrollState()),
        ) {
            val tabs = remember {
                listOf(
                    "Horizontal",
                    "Horizontal Animated",
                    "Vertical",
                    "Vertical Animated"
                )
            }
            var selectedTabIndex by remember { mutableStateOf(0) }

            ScrollableTabRow(
                edgePadding = 0.dp,
                selectedTabIndex = selectedTabIndex,
                backgroundColor = ColorPrimary
            ) {
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        selectedContentColor = MaterialTheme.colors.background,
                        onClick = { selectedTabIndex = tabs.indexOf(item) },
                        text = {
                            Text(
                                text = item,
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> HorizontalItems()
                1 -> HorizontalItems(animationEnabled = true)
                2 -> VerticalItems()
                3 -> VerticalItems(animationEnabled = true)
            }
        }
    }
}

@Composable
private fun HorizontalItems(modifier: Modifier = Modifier, animationEnabled: Boolean = false) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ContentWithBorder {
            TimelineView(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                items = createTimelineItems(
                    lineAnimationEnabled = animationEnabled,
                    pointAnimationEnabled = animationEnabled
                ),
                orientation = TimelineOrientation.HORIZONTAL,
            )
            ItemDescription("point items")
        }
        ContentWithBorder {
            TimelineView(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                items = createTimelineImageItems(
                    lineAnimationEnabled = animationEnabled,
                    pointAnimationEnabled = animationEnabled
                ),
                orientation = TimelineOrientation.HORIZONTAL,
            )

            ItemDescription("image items")
        }
        ContentWithBorder {
            HorizontalTimelineWithIndexTextView(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = createHorizontalWithIndexTimelineItems(),
            )
            ItemDescription("items with intex text")
        }
    }
}

@Composable
private fun VerticalItems(modifier: Modifier = Modifier, animationEnabled: Boolean = false) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ContentWithBorder {
            TimelineView(
                items = createTimelineItems(
                    lineAnimationEnabled = animationEnabled,
                    pointAnimationEnabled = animationEnabled
                ),
                orientation = TimelineOrientation.VERTICAL,
            )
            ItemDescription("point items")
        }
        ContentWithBorder {
            TimelineView(
                items = createTimelineImageItems(
                    lineAnimationEnabled = animationEnabled,
                    pointAnimationEnabled = animationEnabled
                ),
                orientation = TimelineOrientation.VERTICAL,
            )
            ItemDescription("image items")
        }
    }
}


@Composable
private fun ContentWithBorder(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = ColorPrimary, shape = RoundedCornerShape(4.dp))
            .padding(4.dp),
        content = content,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
}

@Composable
private fun ItemDescription(description: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(vertical = 12.dp),
        text = description,
        color = Color.DarkGray,
        fontStyle = FontStyle.Italic,
    )
}
