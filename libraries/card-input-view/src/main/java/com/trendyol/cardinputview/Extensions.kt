package com.trendyol.cardinputview

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

internal fun <T : ViewDataBinding> ViewGroup?.inflate(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = true
): T = DataBindingUtil.inflate(
    LayoutInflater.from(this!!.context),
    layoutId,
    this,
    attachToParent
)

@ColorInt
internal fun Context.color(@ColorRes colorResId: Int): Int =
    ContextCompat.getColor(this, colorResId)

internal fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResId)

internal fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    postDelayed({
        requestFocus()
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, 100L)
}

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
