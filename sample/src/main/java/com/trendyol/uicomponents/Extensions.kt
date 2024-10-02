package com.trendyol.uicomponents

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.appcompat.content.res.AppCompatResources

// from https://stackoverflow.com/a/55573791
fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

fun Context.drawable(@DrawableRes resId: Int): Drawable =
    AppCompatResources.getDrawable(this, resId) ?: throw NullPointerException("nooooo")

fun Context.asSP(value: Int): Int =
    (value * resources.displayMetrics.scaledDensity).toInt()

fun Context.asDP(value: Int): Int =
    (value * resources.displayMetrics.density).toInt()

@FontRes
fun Context.themeFont(@AttrRes attribute: Int): Int {
    val typedValue = TypedValue()
    if (theme.resolveAttribute(attribute, typedValue, true)) {
        return typedValue.resourceId
    } else {
        throw IllegalArgumentException("[android.util.TypedValue] has returned 0 for given resId=$attribute")
    }
}
