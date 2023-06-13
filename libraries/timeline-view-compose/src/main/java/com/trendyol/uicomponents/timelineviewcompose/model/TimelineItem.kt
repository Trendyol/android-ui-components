package com.trendyol.uicomponents.timelineviewcompose.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

sealed class TimelineItem {

    @Stable
    data class Point(
        val text: String,
        val contentMargin: Dp = TimelineViewDefaults.ContentMargin,
        val lineConfig: LineConfig,
        val pointConfig: PointConfig,
        val textStyle: TextStyle = TimelineViewDefaults.TextStyle,
    ) : TimelineItem()

    @Stable
    data class Image(
        val text: String,
        val contentMargin: Dp = TimelineViewDefaults.ContentMargin,
        val imageContentDescription: String? = null,
        val imageConfig: ImageConfig,
        val lineConfig: LineConfig,
        val textStyle: TextStyle = TimelineViewDefaults.TextStyle,
    ) : TimelineItem()
}