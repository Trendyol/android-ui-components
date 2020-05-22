package com.trendyol.uicomponents.touchdelegator

import android.view.MotionEvent
import android.view.Window

class ActivityWindowTouchDelegator constructor(): WindowTouchListener {

    private val touchListeners = mutableSetOf<WindowTouchListener>()

    fun addListener(windowTouchListener: WindowTouchListener) {
        touchListeners.add(windowTouchListener)
    }

    override fun onTouchEvent(window: Window, event: MotionEvent): Boolean {
        for (touchListener in touchListeners) {
            if (touchListener.onTouchEvent(window, event)) {
                return true
            }
        }
        return false
    }

    fun getWindowTouchListeners() = touchListeners
}
