package com.trendyol.uicomponents.dialogs

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("drawable")
internal fun AppCompatImageView.setDrawable(@DrawableRes drawableResId: Int) {
    if (drawableResId != 0) {
        visibility = View.VISIBLE
        setImageResource(drawableResId)
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("isVisible")
internal fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
