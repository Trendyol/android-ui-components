package com.trendyol.suggestioninputview

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

data class SuggestionInputViewState(
    val items: List<SuggestionInputItemViewState>,
    val buttonBackground: Drawable?,
    val editTextBackground: Drawable?,
    val editTextErrorBackground: Drawable?,
    @ColorInt val buttonTextColor: Int,
    @Dimension val textSize: Float,
    val buttonText: String,
    val verticalPadding: Float,
    val suffix: String,
    val inputType: Int,
    val shouldShowInputItemError: Boolean,
    val inputErrorMessage: String,
    val hint: String
) {
    fun getInputBackground(): Drawable? =
        if(shouldShowInputItemError) {
            editTextErrorBackground
        } else {
            editTextBackground
        }

    fun getMinWidth() = items.firstOrNull()?.getMinimumWidth()

    fun getErrorTextVisiblity(): Int = if(shouldShowInputItemError) View.VISIBLE else View.GONE

    fun getErrorText(): String = inputErrorMessage

    fun isSuffixVisible(): Boolean = hint.isEmpty() && suffix.isNotEmpty()
}