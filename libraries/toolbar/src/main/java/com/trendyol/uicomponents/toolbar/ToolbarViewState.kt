package com.trendyol.uicomponents.toolbar

import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes

data class ToolbarViewState(
    val upperLeftText: CharSequence? = null,
    val lowerLeftText: CharSequence? = null,
    val middleText: CharSequence? = null,
    val upperRightText: CharSequence? = null,
    val lowerRightText: CharSequence? = null,
    @DrawableRes val leftImageDrawableResId: Int? = null,
    @DrawableRes val middleImageDrawableResId: Int? = null,
    @DrawableRes val rightImageDrawableResId: Int? = null,
    @StyleRes val upperLeftTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val lowerLeftTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @StyleRes val middleTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_Title,
    @StyleRes val upperRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val lowerRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction
) {

    fun isUpperLeftTextVisible(): Boolean = upperLeftText != null

    fun isLowerLeftTextVisible(): Boolean = lowerLeftText != null

    fun isMiddleTextVisible(): Boolean = middleText != null

    fun isUpperRightTextVisible(): Boolean = upperRightText != null

    fun isLowerRightTextVisible(): Boolean = lowerRightText != null

    fun isLeftImageVisible(): Boolean = leftImageDrawableResId != null

    fun isMiddleImageVisible(): Boolean = middleImageDrawableResId != null

    fun isRightImageVisible(): Boolean = rightImageDrawableResId != null
}
