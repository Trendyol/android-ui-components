package com.trendyol.suggestioninputview

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

data class SuggestionInputViewState(
    val items: List<SuggestionInputItemViewState>,
    val buttonBackground: Drawable?,
    val editTextBackground: Drawable?,
    @ColorInt val buttonTextColor: Int,
    @Dimension val textSize: Float,
    val buttonText: String,
    val verticalPadding: Float,
    val suffix: String,
    val inputType: Int
)