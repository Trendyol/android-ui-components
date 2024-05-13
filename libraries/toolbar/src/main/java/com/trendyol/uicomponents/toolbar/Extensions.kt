package com.trendyol.uicomponents.toolbar

import android.content.res.TypedArray
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
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

internal fun AppCompatImageView.setVerticalPadding(padding: Int?) {
    if (padding == null) return
    this.setPadding(0, padding, 0, padding)
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

internal fun TypedArray.getDimensionPixelOffsetOrDefault(
    @StyleableRes primaryIndex: Int,
    @StyleableRes secondaryIndex: Int,
    defaultValue: Int,
): Int {
    val primaryValue = getDimensionPixelOffset(primaryIndex, -1)
    val secondaryValue = getDimensionPixelOffset(secondaryIndex, -1)
    return if (primaryValue == -1 && secondaryValue == -1) {
        defaultValue
    } else if (primaryValue == -1) {
        secondaryValue
    } else {
        primaryValue
    }
}

internal fun TypedArray.getResourceIdOrDefault(
    @StyleableRes primaryIndex: Int,
    @StyleableRes secondaryIndex: Int,
    defaultValue: Int
): Int {
    val primaryValue = getResourceId(primaryIndex, 0)
    val secondaryValue = getResourceId(secondaryIndex, 0)
    return if (primaryValue == 0 && secondaryValue == 0) {
        defaultValue
    } else if (primaryValue == 0) {
        secondaryValue
    } else {
        primaryValue
    }
}

internal fun TypedArray.getStringOrEmpty(
    @StyleableRes primaryIndex: Int,
    @StyleableRes secondaryIndex: Int,
): String {
    val primaryValue = getString(primaryIndex)
    val secondaryValue = getString(secondaryIndex)
    return primaryValue ?: secondaryValue ?: ""
}
