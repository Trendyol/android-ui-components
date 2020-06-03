package com.trendyol.uicomponents.quantitypickerview

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

internal fun Context.asSP(value: Int): Int =
    (value * resources.displayMetrics.scaledDensity).toInt()

internal fun Context.asDP(value: Int): Int =
    (value * resources.displayMetrics.density).toInt()

internal fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}
