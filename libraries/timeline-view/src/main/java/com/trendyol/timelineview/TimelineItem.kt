package com.trendyol.timelineview

data class TimelineItem(
    val outsideColor: String,
    val insideColor: String,
    val text: String,
    val textColor: String,
    val leftLineColor: String? = null,
    val rightLineColor: String? = null
)