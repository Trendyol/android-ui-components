package com.trendyol.timelineview

data class TimelineViewState(
    val timelineItems: List<TimelineItemViewState>,
    val timelineOrientation: TimelineOrientation
)