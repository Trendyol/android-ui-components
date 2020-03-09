package com.trendyol.cardinputview

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("civ_cardInfoLogoUrl")
internal fun ImageView.setCardInfoLogoUrl(imageUrl: String?) {
    if (imageUrl?.isNotEmpty() == true) {
        Glide.with(context).load(imageUrl).into(this)
    } else {
        setImageDrawable(null)
    }
}
