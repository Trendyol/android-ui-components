package com.trendyol.uicomponents.toolbar

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.TextViewCompat

internal fun AppCompatImageView.setDrawableResource(@DrawableRes drawableResId: Int) {
    if (drawableResId != 0) {
        visibility = View.VISIBLE
        setImageResource(drawableResId)
    } else {
        visibility = View.GONE
    }
}

internal fun TextView.setStyle(@StyleRes styleResId: Int) {
    TextViewCompat.setTextAppearance(this, styleResId)
}

internal fun View.setStartMargin(startMargin: Int?) {
    if (startMargin == null) return
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).also {
        it.marginStart = startMargin
    }
}

internal fun View.setEndMargin(endMargin: Int?) {
    if (endMargin == null) return
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).also {
        it.marginEnd = endMargin
    }
}

/**
 * Set debounce time(millis) to onClickListener for prevent multiple clicks.
 * @param debounceMillis: Millis time that gives delay between clicks.
 * @param onClickListener: Lambda function that invokes click listener.
 */
fun View.setDebouncedOnClickListener(debounceMillis: Long = 500L, onClickListener: View.OnClickListener) {
    var lastClickTime: Long = 0
    setOnClickListener { view ->
        val shouldAcceptClick = (System.currentTimeMillis() - lastClickTime) > debounceMillis
        if (shouldAcceptClick) {
            lastClickTime = System.currentTimeMillis()
            onClickListener.onClick(view)
        }
    }
}
