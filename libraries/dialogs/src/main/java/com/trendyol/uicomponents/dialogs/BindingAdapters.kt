package com.trendyol.uicomponents.dialogs

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
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

@BindingAdapter("items")
internal fun RecyclerView.setItems(items: List<Pair<Boolean, CharSequence>>?) {
    if (items != null) {
        (adapter as? DialogListAdapter)?.setItems(items)
    }
}
