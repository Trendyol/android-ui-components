package com.trendyol.uicomponents.toolbar

import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes

data class ToolbarViewState(
    val upperLeftText: CharSequence? = null,
    val lowerLeftText: CharSequence? = null,
    val middleText: CharSequence? = null,
    val upperRightText: CharSequence? = null,
    val lowerRightText: CharSequence? = null,
    @DrawableRes val leftImageDrawableResId: Int = R.drawable.ic_arrow_back,
    @DrawableRes val middleImageDrawableResId: Int = 0,
    @DrawableRes val rightImageDrawableResId: Int = 0,
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
}
