package com.trendyol.suggestioninputview

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

data class SuggestionInputBadge(
    val text: String = "",
    val background: Drawable?,
    @ColorInt val textColor: Int,
    @Dimension val textSize: Float,
    @Dimension val horizontalPadding: Float,
    @Dimension val verticalPadding: Float
)
