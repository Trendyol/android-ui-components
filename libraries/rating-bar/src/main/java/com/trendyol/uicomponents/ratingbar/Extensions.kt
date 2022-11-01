package com.trendyol.uicomponents.ratingbar

import android.content.Context
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

@ColorInt
internal fun Context.color(@ColorRes colorResId: Int): Int =
    ContextCompat.getColor(this, colorResId)

internal fun ImageView.setTintCompat(@ColorInt tint: Int) {
    drawable.mutate()
    DrawableCompat.setTint(drawable, tint)
}
