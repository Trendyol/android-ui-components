package com.trendyol.uicomponents.toolbar

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("toolbarDrawableResource")
internal fun AppCompatImageView.setDrawableResource(@DrawableRes drawableResId: Int) {
    if (drawableResId != 0) {
        visibility = View.VISIBLE
        setImageResource(drawableResId)
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("toolbarIsVisible")
internal fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("textAppearance")
fun TextView.setStyle(@StyleRes styleResId: Int) {
    TextViewCompat.setTextAppearance(this, styleResId)
}
