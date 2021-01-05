package com.trendyol.timelineview

data class TimelineItem(
    val outsideColor: String,
    val insideColor: String,
    val text: String,
    val textColor: String,
    val startLineColor: String? = null,
    val endLineColor: String? = null
)