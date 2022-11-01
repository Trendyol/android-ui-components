package com.trendyol.suggestioninputview

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@ColorInt
internal fun Context.color(@ColorRes colorResId: Int): Int =
    ContextCompat.getColor(this, colorResId)

internal fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResId)

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
