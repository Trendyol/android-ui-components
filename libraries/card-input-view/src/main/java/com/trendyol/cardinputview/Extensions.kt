package com.trendyol.cardinputview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@ColorInt
internal fun Context.color(@ColorRes colorResId: Int): Int =
    ContextCompat.getColor(this, colorResId)

internal fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResId)

internal fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    postDelayed({
        requestFocus()
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, 100L)
}

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

internal fun String.removeNonDigitCharacters() = replace("[^\\d]".toRegex(), "")

internal fun Editable.updateText(currentText: String) {
    val currentFilters = filters
    filters = arrayOf<InputFilter>()
    replace(0, length, currentText, 0, currentText.length)
    filters = currentFilters
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