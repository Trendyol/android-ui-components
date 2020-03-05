package com.trendyol.cardinputview

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("civ_cardInfoLogoDrawable")
internal fun ImageView.setCardInfoLogo(logoDrawable: Drawable?) =
    setImageDrawable(logoDrawable)
