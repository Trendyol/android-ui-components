package com.trendyol.cardinput

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

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

@ColorInt
internal fun Context.color(@ColorRes colorResId: Int): Int =
    ContextCompat.getColor(this, colorResId)

internal fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResId)

@BindingAdapter("isVisible")
internal fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
