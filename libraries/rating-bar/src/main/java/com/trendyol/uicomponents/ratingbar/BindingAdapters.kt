package com.trendyol.uicomponents.ratingbar

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("tintCompat")
    @JvmStatic
    internal fun ImageView.setTintCompat(@ColorInt tint: Int) {
        drawable.mutate()
        DrawableCompat.setTint(drawable, tint)
    }
}
