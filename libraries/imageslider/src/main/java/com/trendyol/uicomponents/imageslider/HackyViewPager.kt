package com.trendyol.uicomponents.imageslider

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager

class HackyViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (ex: Exception) {
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (currentItem == 0 && childCount == 0) {
            false
        } else super.onTouchEvent(ev)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var wrappedHeightMeasureSpec = heightMeasureSpec

        var height = 0
        children.forEach { child ->
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }

        if (height != 0) {
            wrappedHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, wrappedHeightMeasureSpec)
    }
}
