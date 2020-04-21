package com.trendyol.uicomponents.toolbar

import android.view.View
import android.view.ViewGroup
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

@BindingAdapter("toolbarTextAppearance")
internal fun TextView.setStyle(@StyleRes styleResId: Int) {
    TextViewCompat.setTextAppearance(this, styleResId)
}

@BindingAdapter("toolbar_layout_marginLeft")
fun setLeftMargin(view: View, leftMargin: Int?) {
    leftMargin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            leftMargin, layoutParams.topMargin,
            layoutParams.rightMargin, layoutParams.bottomMargin
        )
        view.layoutParams = layoutParams
    }
}

@BindingAdapter("toolbar_layout_marginRight")
fun setRightMargin(view: View, rightMargin: Int?) {
    rightMargin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            layoutParams.leftMargin, layoutParams.topMargin,
            rightMargin, layoutParams.bottomMargin
        )
        view.layoutParams = layoutParams
    }
}

@BindingAdapter("toolbar_layout_marginTop")
fun setTopMargin(view: View, topMargin: Int?) {
    topMargin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            layoutParams.leftMargin, topMargin,
            layoutParams.rightMargin, layoutParams.bottomMargin
        )
        view.layoutParams = layoutParams
    }
}

@BindingAdapter("toolbar_layout_marginBottom")
fun setBottomMargin(view: View, bottomMargin: Int?) {
    bottomMargin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            layoutParams.leftMargin, layoutParams.topMargin,
            layoutParams.rightMargin, bottomMargin
        )
        view.layoutParams = layoutParams
    }
}

@BindingAdapter("toolbar_layout_marginStart")
fun setStartMargin(view: View, startMargin: Int?) {
    startMargin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = it
        view.layoutParams = layoutParams
    }
}

@BindingAdapter("toolbar_layout_marginEnd")
fun setEndMargin(view: View, endMargin: Int?) {
    endMargin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginEnd = it
        view.layoutParams = layoutParams
    }
}


