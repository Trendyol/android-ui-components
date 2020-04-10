package com.trendyol.timelineview

import android.graphics.Color
import android.view.View

class TimelineItemViewState(
    private val timelineItem: TimelineItem,
    val dotSize: Float,
    val borderWidth: Float,
    val textSize: Float
) {

    fun getOutsideColor(): Int = Color.parseColor(timelineItem.outsideColor)

    fun getInsideColor(): Int = Color.parseColor(timelineItem.insideColor)

    fun getText(): String = timelineItem.text

    fun getTextColor(): Int = Color.parseColor(timelineItem.textColor)

    fun getLeftLineColor(): Int = Color.parseColor(timelineItem.leftLineColor ?: "#000000")

    fun getRightLineColor(): Int = Color.parseColor(timelineItem.rightLineColor ?: "#000000")

    fun getLeftLineVisibility(): Int = if(timelineItem.leftLineColor != null) View.VISIBLE else View.GONE

    fun getRightLineVisibility(): Int = if(timelineItem.rightLineColor != null) View.VISIBLE else View.GONE

    fun getOutsideDotSize(): Float = dotSize

    fun getInsideDotSize(): Float = (dotSize - (borderWidth * 2))

    fun getShadowDotSize(): Float = (dotSize + borderWidth)
}