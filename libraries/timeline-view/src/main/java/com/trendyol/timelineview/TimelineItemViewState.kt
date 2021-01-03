package com.trendyol.timelineview

import android.graphics.Color
import android.view.View

class TimelineItemViewState(
    private val timelineItem: TimelineItem,
    val dotSize: Float,
    val borderWidth: Float,
    val textSize: Float,
    val lineWidth: Float,
    val fontFamily: String
) {

    fun getOutsideColor(): Int = Color.parseColor(timelineItem.outsideColor)

    fun getInsideColor(): Int = Color.parseColor(timelineItem.insideColor)

    fun getText(): String =
        timelineItem.text.replaceFirst(" ","\n")

    fun getTextColor(): Int = Color.parseColor(timelineItem.textColor)

    fun getStartLineColor(): Int = Color.parseColor(timelineItem.startLineColor ?: hexColorWhite)

    fun getEndLineColor(): Int = Color.parseColor(timelineItem.endLineColor ?: hexColorWhite)

    fun getStartLineVisibility(): Int =
        if (timelineItem.startLineColor != null) View.VISIBLE else View.GONE

    fun getEndLineVisibility(): Int =
        if (timelineItem.endLineColor != null) View.VISIBLE else View.GONE

    fun getOutsideDotSize(): Float = dotSize

    fun getInsideDotSize(): Float = dotSize - (borderWidth * 2)

    fun getShadowDotSize(): Float = dotSize + borderWidth

    fun getItemWidth(): Float =
        dotSize + borderWidth + lineWidth

    companion object {
        private const val hexColorWhite = "#000000"
    }
}