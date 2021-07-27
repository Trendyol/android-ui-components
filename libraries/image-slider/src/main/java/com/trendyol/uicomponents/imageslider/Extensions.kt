package com.trendyol.uicomponents.imageslider

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlin.math.pow
import kotlin.math.sqrt

internal fun Int.greaterThan(number: Int): Boolean = this > number

internal fun <T : ViewDataBinding> ViewGroup?.inflate(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = true
): T {
    if (this?.isInEditMode == true) {
        View.inflate(context, layoutId, parent as? ViewGroup?)
    }
    return DataBindingUtil.inflate(
        LayoutInflater.from(this!!.context),
        layoutId,
        this,
        attachToParent
    )
}

fun ImageView.loadImage(imageUrl: String, cornerRadiusInDp: Double?) {
    val requestBuilder = Glide.with(context).load(imageUrl)
    if (cornerRadiusInDp == null || cornerRadiusInDp == 0.0) {
        requestBuilder
    } else {
        requestBuilder.transform(RoundedCorners(dpToPx(cornerRadiusInDp)))
    }.into(this)
}

internal val RETURN_ANIM_DURATION = 350

internal fun Point.getDifferenceVector(b: Point): Point {
    return Point(b.x - x, b.y - y)
}

internal fun MotionEvent.calculateAverageTouch(): Point {
    var totalX = 0
    var totalY = 0

    for (i in 0 until pointerCount) {
        totalX += getX(i).toInt()
        totalY += getY(i).toInt()
    }

    return Point(totalX / pointerCount, totalY / pointerCount)
}

internal fun calculateDistance(x: Float, y: Float, x1: Float, y1: Float): Float {
    return sqrt((x - x1).toDouble().pow(2.0) + (y - y1).toDouble().pow(2.0)).toFloat()
}

internal fun View.getScaledHeight(): Int {
    return (scaleY * height).toInt()
}

internal fun View.getScaledWidth(): Int {
    return (scaleX * width).toInt()
}

internal fun View.setPivot(point: Point) {
    pivotY = point.y.toFloat()
    pivotX = point.x.toFloat()
}

internal fun View.setScale(scaleFactor: Float) {
    if (scaleFactor in Float.MIN_VALUE..Float.MAX_VALUE) {
        scaleY = scaleFactor
        scaleX = scaleFactor
    }
}

internal fun View.getViewPosition(): Point {
    val location = intArrayOf(0, 0)
    getLocationInWindow(location)
    return Point(location[0], location[1])
}

internal fun View.createFinishAnimation(targetView: View): ViewPropertyAnimator {
    val targetPoint = targetView.getViewPosition()
    return animate().x(targetPoint.x.toFloat()).y(targetPoint.y.toFloat())
        .setInterpolator(AccelerateDecelerateInterpolator()).scaleX(1f).scaleY(1f)
        .setDuration(RETURN_ANIM_DURATION.toLong())
}

internal fun ColorDrawable.createFadeOut(): ObjectAnimator {
    return ObjectAnimator.ofInt(
        this,
        "alpha",
        alpha,
        0
    )
}

internal fun dpToPx(dp: Double): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
