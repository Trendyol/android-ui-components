package com.trendyol.uicomponents.imageslider

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView

import androidx.core.content.ContextCompat

import com.trendyol.uicomponents.imageslider.touchdelegator.ZoomWindowTouchListener

import android.view.MotionEvent.ACTION_POINTER_INDEX_MASK
import android.view.MotionEvent.ACTION_POINTER_INDEX_SHIFT
import android.view.MotionEvent.ACTION_POINTER_UP
import com.bumptech.glide.util.Util
import kotlin.math.sqrt

@SuppressLint("ClickableViewAccessibility")
class CVFloatingZoomView(
    context: Context,
    val originalImage: ImageView,
    private val zoomWindowTouchListener: ZoomWindowTouchListener?
) : FrameLayout(context), View.OnTouchListener {

    private val blackBackground: ColorDrawable =
        ColorDrawable(ContextCompat.getColor(context, android.R.color.black))

    private var isBusy: Boolean = false
    private var isAnimPlaying: Boolean = false

    private val rootLayout: ViewGroup = this
    private var floatingImage: ImageView = ImageView(context)

    private var initialPoint: Point? = null
    private var initialZoomPoint: Point? = null
    private var lastCenter: Point? = null
    private var currentCenter: Point? = null
    private var remainingFingerIndex: Int? = null
    private var initialDifference: Float = 0.toFloat()
    private var currentDistance: Float = 0.toFloat()
    private var leftoverZoom: Float = 0.toFloat()

    private var currentFingerSwapState: FingerSwapState? = null

    private var semiBusy = false

    private enum class FingerSwapState {
        ONE_FINGER,
        TWO_FINGER
    }

    init {
        rootLayout.background = blackBackground
        blackBackground.alpha = 0
        originalImage.setOnTouchListener(this)
    }

    private fun isInteractionStartEvent(event: MotionEvent, motionEvent: Int): Boolean {
        return motionEvent == MotionEvent.ACTION_POINTER_DOWN && event.pointerCount == 2
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val motionEvent = event.action and MotionEvent.ACTION_MASK
        val index = event.action and ACTION_POINTER_INDEX_MASK shr ACTION_POINTER_INDEX_SHIFT
        currentCenter = event.calculateAverageTouch()
        if (!isBusy && event.action == MotionEvent.ACTION_DOWN) {
            semiBusy = true
        }
        if (semiBusy && (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_UP)) {
            semiBusy = false
        }

        if (!isBusy && isInteractionStartEvent(event, motionEvent)) {
            initialDifference =
                calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
            initialPoint = Point(currentCenter!!.x, currentCenter!!.y)
            initialZoomPoint = Point(initialPoint!!.x, initialPoint!!.y)
            leftoverZoom = 0f
        } else if (isBusy && isInteractionStartEvent(event, motionEvent)) {
            onFingerCountChange(event, FingerSwapState.TWO_FINGER)
            leftoverZoom = floatingImage.scaleX - 1
            initialDifference =
                calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
        } else if (!isBusy && motionEvent == MotionEvent.ACTION_MOVE && event.pointerCount > 1) {
            currentDistance =
                calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
            val zoomRatio = leftoverZoom + currentDistance / initialDifference
            if (zoomRatio > ZOOM_THRESHOLD) {
                onInteractionStart()
            }
        } else if (isBusy && motionEvent == MotionEvent.ACTION_MOVE) {
            if (event.pointerCount > 1) {
                currentDistance = calculateDistance(
                    event.getX(0),
                    event.getY(0),
                    event.getX(1),
                    event.getY(1)
                )
                val zoomRatio = leftoverZoom + currentDistance / initialDifference

                if (initialZoomPoint == null) {
                    initialZoomPoint = currentCenter
                }

                if (shouldMove()) {
                    onFloatingImageMove(currentCenter)
                }
                onFloatingImageZoom(zoomRatio, initialZoomPoint)


            } else {
                if (currentFingerSwapState == FingerSwapState.TWO_FINGER) {
                    onFingerCountChange(event, FingerSwapState.ONE_FINGER)
                } else if (currentFingerSwapState == FingerSwapState.ONE_FINGER) {
                    onFloatingImageMove(currentCenter)
                }
            }

        }
        //EXIT STATES
        if (isBusy) {
            if (motionEvent == MotionEvent.ACTION_UP && event.pointerCount == 1) {
                playEndAnimation()
            } else if (motionEvent == ACTION_POINTER_UP && remainingFingerIndex != null && remainingFingerIndex == index) {
                playEndAnimation()
            } else if (motionEvent == MotionEvent.ACTION_CANCEL) {
                playEndAnimation()
            }
        }
        if (!semiBusy && !isBusy && event.action == MotionEvent.ACTION_UP) {
            (originalImage.parent as View).performClick()
        }
        lastCenter = currentCenter
        return semiBusy || isBusy
    }

    private fun shouldMove(): Boolean {
        return lastCenter != null && !isAnimPlaying
    }

    private fun onInteractionStart() {
        if (isBusy && context !is Activity) {
            return
        }
        originalImage.visibility = View.INVISIBLE
        initViews()
        initPositions()
        (context as Activity).addContentView(rootLayout, rootLayout.layoutParams)
        currentFingerSwapState = FingerSwapState.TWO_FINGER
        isBusy = true
        zoomWindowTouchListener?.onHandlingStart(this)
    }

    @SuppressLint("RtlHardcoded")
    private fun initViews() {

        rootLayout.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        floatingImage.layoutParams = LayoutParams(
            originalImage.width, originalImage.height, Gravity.LEFT or Gravity.TOP
        )
        val point = originalImage.getViewPosition()
        (floatingImage.layoutParams as MarginLayoutParams).leftMargin = point.x
        (floatingImage.layoutParams as MarginLayoutParams).topMargin = point.y
        floatingImage.setImageDrawable(originalImage.drawable)
        floatingImage.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    private fun initPositions() {

        floatingImage.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        rootLayout.measure(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
        )

        rootLayout.addView(floatingImage, floatingImage.layoutParams)
        floatingImage.setPivot(
            Point(floatingImage.getScaledWidth() / 2, floatingImage.getScaledHeight() / 2)
        )
        floatingImage.visibility = View.VISIBLE
        floatingImage.scaleY = 1f
        floatingImage.scaleX = 1f
    }


    private fun onFloatingImageMove(point: Point?) {
        point?.let {
            if (initialPoint == null) {
                initialPoint = it
            }
            floatingImage.measure(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            val differenceVector = initialPoint!!.getDifferenceVector(it)
            (floatingImage.layoutParams as MarginLayoutParams).leftMargin =
                (originalImage.x + differenceVector.x).toInt()
            (floatingImage.layoutParams as MarginLayoutParams).topMargin =
                (originalImage.y + differenceVector.y).toInt()
            floatingImage.requestLayout()
        }
    }

    private fun onFloatingImageZoom(scaleFactor: Float, focusPoint: Point?) {
        focusPoint?.let {
            if (floatingImage.scaleX * scaleFactor > 1) {
                floatingImage.setPivot(it)
                floatingImage.setScale(scaleFactor)
                val totalScaleFactor = scaleFactor * floatingImage.scaleX // ie. 1.2
                val scaleRatio = totalScaleFactor - 1 //ie. 0.2 = % 20
                val zoomRatio = sqrt(scaleRatio.toDouble()).toFloat()
                val backgroundAlpha = 0.0.coerceAtLeast(
                    (MAX_ALPHA_VALUE * FADEOUT_FACTOR).coerceAtMost(zoomRatio.toDouble() * MAX_ALPHA_VALUE.toDouble() * FADEOUT_FACTOR)
                ).toInt()
                blackBackground.alpha = backgroundAlpha
            }
        }
    }

    private fun playEndAnimation() {
        if (!isAnimPlaying) {
            val endAnimation = floatingImage.createFinishAnimation(originalImage)
            val fadeAnimation = blackBackground.createFadeOut()
            endAnimation.setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    isAnimPlaying = true
                }

                override fun onAnimationEnd(animation: Animator) {
                    isAnimPlaying = false
                    onInteractionEnd()
                }

                override fun onAnimationCancel(animation: Animator) {
                    isAnimPlaying = false
                    onInteractionEnd()
                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
            fadeAnimation.setDuration(endAnimation.duration).start()
            endAnimation.start()
        }
    }

    private fun onFingerCountChange(event: MotionEvent, newstate: FingerSwapState) {
        initialPoint!!.set(
            initialPoint!!.x - (lastCenter!!.x - currentCenter!!.x),
            initialPoint!!.y - (lastCenter!!.y - currentCenter!!.y)
        )
        lastCenter = currentCenter
        remainingFingerIndex =
            1 - (event.action and ACTION_POINTER_INDEX_MASK shr ACTION_POINTER_INDEX_SHIFT)
        currentFingerSwapState = newstate
    }

    fun onInteractionEnd() {
        if (isBusy && rootLayout.parent != null) {
            isBusy = false
            zoomWindowTouchListener?.onHandlingEnd(this)
            originalImage.visibility = View.VISIBLE
            (rootLayout.parent as ViewGroup).removeView(rootLayout)
            initialPoint = null
            initialZoomPoint = null
            lastCenter = null
            currentCenter = null
            remainingFingerIndex = null
            initialDifference = 0f
            floatingImage.pivotX = (floatingImage.getScaledWidth() / 2).toFloat()
            floatingImage.pivotY = (floatingImage.getScaledHeight() / 2).toFloat()
            rootLayout.removeView(floatingImage)
            currentFingerSwapState = null
            originalImage.invalidate()
            originalImage.requestLayout()
        }
    }

    interface FloatingZoomListener {

        fun onHandlingStart(instance: CVFloatingZoomView?)

        fun onHandlingEnd(instance: CVFloatingZoomView?)
    }

    companion object {

        val ZOOM_THRESHOLD = 1.02
        val FADEOUT_FACTOR = (1 / 2).toDouble()
        val MAX_ALPHA_VALUE = 255
    }
}
