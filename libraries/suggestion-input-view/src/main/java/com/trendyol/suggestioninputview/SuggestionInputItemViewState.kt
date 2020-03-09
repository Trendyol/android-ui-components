package com.trendyol.suggestioninputview

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

data class SuggestionInputItemViewState(
    val id: Int,
    val type: SuggestionItemType,
    val isSelected: Boolean,
    val text: String,
    val value: String,
    val selectedBackground: Drawable?,
    val unselectedBackground: Drawable?,
    val errorBackground: Drawable?,
    val suffix: String,
    val shouldShowSelectableItemError: Boolean,
    @ColorInt val selectedTextColor: Int,
    @ColorInt val unselectedTextColor: Int,
    @Dimension val textSize: Float,
    @Dimension val horizontalPadding: Float,
    @Dimension val verticalPadding: Float,
    @Dimension val minWidth: Float,
    val isEnable: Boolean,
    val disabledItemBackground: Drawable?
) {
    fun getBackground(): Drawable? = when {
        isEnable.not() -> disabledItemBackground
        shouldShowSelectableItemError -> errorBackground
        isSelected -> selectedBackground
        else -> unselectedBackground
    }

    fun isItemEnabled(): Boolean = isEnable

    fun getTextColor(): Int =
        if (isSelected) selectedTextColor else unselectedTextColor

    fun getMinWidth(): Int = minWidth.toInt()
}