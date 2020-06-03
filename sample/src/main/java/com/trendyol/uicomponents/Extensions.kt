package com.trendyol.uicomponents

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
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
