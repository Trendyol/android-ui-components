package com.trendyol.suggestioninputview

import android.graphics.drawable.Drawable
import android.view.View
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
    val badge: SuggestionInputBadge,
    @ColorInt val selectedTextColor: Int,
    @ColorInt val unselectedTextColor: Int,
    @Dimension val textSize: Float,
    @Dimension val horizontalPadding: Float,
    @Dimension val verticalPadding: Float,
    @Dimension val minWidth: Float
) {
    fun getBackground(): Drawable? = when {
        shouldShowSelectableItemError -> errorBackground
        isSelected -> selectedBackground
        else -> unselectedBackground
    }

    fun getTextColor(): Int =
        if (isSelected) selectedTextColor else unselectedTextColor

    fun getMinimumWidth(): Int = minWidth.toInt()

    fun getBadgeVisibility(): Int = if (badge.text.isNotEmpty()) View.VISIBLE else View.GONE

    fun getBadgeBackground(): Drawable? = badge.background

    fun getBadgeVerticalPadding(): Float = badge.verticalPadding

    fun getBadgeHorizontalPadding(): Float = badge.horizontalPadding

    fun getBadgeText(): String = badge.text

    fun getBadgeTextColor(): Int = badge.textColor

    fun getBadgeTextSize(): Float = badge.textSize
}