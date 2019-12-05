package com.trendyol.uicomponents.imageslider

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide


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

private const val PRODUCT_LIST_IMAGE_RATIO = 1

internal fun ImageView.loadProductImageWithWidth(
    imageUrl: String,
    width: Int
) {
    val height = (width * PRODUCT_LIST_IMAGE_RATIO).toInt()
    loadProductImage(imageUrl, width, height)
}

internal fun ImageView.loadProductImageWithHeight(
    imageUrl: String,
    height: Int
) {
    val width = (height / PRODUCT_LIST_IMAGE_RATIO).toInt()
    loadProductImage(imageUrl, width, height)
}

internal fun ImageView.loadProductImage(imageUrl: String, width: Int, height: Int) {
    updateImageViewSize(intArrayOf(width, height))
    Glide.with(context)
        .load(imageUrl)
        .into(this)
}

internal fun ImageView.updateImageViewSize(size: IntArray) {
    layoutParams = layoutParams.apply {
        width = size[0]
        height = size[1]
    }
}

internal fun Context.deviceWidth(): Int {
    (getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.let {
        val metrics = DisplayMetrics()
        it.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    } ?: return 0
}