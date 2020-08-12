package com.trendyol.timelineview

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

class SmoothScroller(
    context: Context,
    private val milliSecondsPerInch: Float = MILLISECONDS_PER_INCH
) : LinearSmoothScroller(context) {

    companion object {
        const val MILLISECONDS_PER_INCH = 240f // default is 25f (bigger = slower)
    }

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return milliSecondsPerInch / displayMetrics.densityDpi
    }
}