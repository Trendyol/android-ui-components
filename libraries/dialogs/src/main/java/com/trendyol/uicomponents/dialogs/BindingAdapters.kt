package com.trendyol.uicomponents.dialogs

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.uicomponents.dialogs.list.DialogListAdapter

@BindingAdapter("drawableResource")
internal fun AppCompatImageView.setDrawableResource(@DrawableRes drawableResId: Int?) {
    if (drawableResId != null) {
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


@BindingAdapter("textColorResource")
internal fun TextView.setTextColorResource(@ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
}