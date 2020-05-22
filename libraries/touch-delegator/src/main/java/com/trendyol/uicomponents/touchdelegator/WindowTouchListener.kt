package com.trendyol.uicomponents.touchdelegator

import android.view.MotionEvent
import android.view.Window

interface WindowTouchListener {

    fun onTouchEvent(window: Window, event: MotionEvent): Boolean
}
