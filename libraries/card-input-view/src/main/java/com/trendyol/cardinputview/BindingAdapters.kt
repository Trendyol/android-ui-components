package com.trendyol.cardinputview

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("isVisible")
internal fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
internal fun ImageView.setImageUrl(imageUrl: String?) {
    if (imageUrl?.isNotEmpty() == true) {
        Glide.with(context).load(imageUrl).into(this)
    } else {
        setImageDrawable(null)
    }
}
