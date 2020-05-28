package com.trendyol.uicomponents.touchdelegator

import android.view.MotionEvent
import android.view.Window

interface WindowTouchDelegator :
    WindowTouchListener {

    fun getWindowTouchListeners(): MutableSet<WindowTouchListener>

    fun addListener(windowTouchListener: WindowTouchListener)

    override fun onTouchEvent(window: Window, event: MotionEvent): Boolean {
        for (touchListener in getWindowTouchListeners()) {
            if (touchListener.onTouchEvent(window, event)) {
                return true
            }
        }
        return false
    }
}