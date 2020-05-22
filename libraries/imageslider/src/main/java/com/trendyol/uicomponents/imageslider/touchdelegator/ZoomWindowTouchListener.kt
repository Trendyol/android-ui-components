package com.trendyol.uicomponents.imageslider.touchdelegator

import android.view.MotionEvent
import android.view.Window
import com.trendyol.uicomponents.imageslider.CVFloatingZoomView
import com.trendyol.uicomponents.touchdelegator.WindowTouchListener

class ZoomWindowTouchListener : WindowTouchListener, CVFloatingZoomView.FloatingZoomListener {

    private var zoomView: CVFloatingZoomView? = null

    override fun onHandlingStart(instance: CVFloatingZoomView?) {
        this.zoomView = instance
    }

    override fun onHandlingEnd(instance: CVFloatingZoomView?) {
        this.zoomView = null
    }

    override fun onTouchEvent(window: Window, event: MotionEvent): Boolean {
        return zoomView?.originalImage?.let { zoomView?.onTouch(it, event) } ?: false
    }
}
