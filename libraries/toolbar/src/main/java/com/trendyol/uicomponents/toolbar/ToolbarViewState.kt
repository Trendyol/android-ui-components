package com.trendyol.uicomponents.toolbar

import android.text.Spanned
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StyleRes
import androidx.core.text.HtmlCompat

data class ToolbarViewState(
    val upperLeftText: CharSequence? = null,
    val lowerLeftText: CharSequence? = null,
    val middleText: CharSequence? = null,
    val upperRightText: CharSequence? = null,
    val lowerRightText: CharSequence? = null,
    @DrawableRes val leftImageDrawableResId: Int = R.drawable.trendyol_uicomponents_toolbar_arrow_back,
    @DrawableRes val middleImageDrawableResId: Int = 0,
    @DrawableRes val rightImageDrawableResId: Int = 0,
    @StyleRes val upperLeftTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val lowerLeftTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @StyleRes val middleTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val upperRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val lowerRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @StyleRes val upperRightTextDisabledAppearance: Int =
        R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction_Disabled,
    @DrawableRes val toolbarBackground: Int = android.R.color.white,
    @Px val upperLeftTextMarginStartInPixel: Int? = null,
    @Px val lowerLeftTextMarginStartInPixel: Int? = null,
    @Px val upperRightTextMarginEndInPixel: Int? = null,
    @Px val lowerRightTextMarginEndInPixel: Int? = null,
    @Px val rightImageDrawableMarginEndInPixel: Int? = null,
    @Px val leftImageDrawableMarginStartInPixel: Int? = null,
    val isUpperRightTextEnabled: Boolean = true,
    val hideLeftImage: Boolean = false
) {

    internal val upperLeftTextVisibility: Int = getFieldVisibility(upperLeftText)
    internal val lowerLeftTextVisibility: Int = getFieldVisibility(lowerLeftText)
    internal val middleTextVisibility: Int = getFieldVisibility(middleText)
    internal val upperRightTextVisibility: Int = getFieldVisibility(upperRightText)
    internal val lowerRightTextVisibility: Int = getFieldVisibility(lowerRightText)

    internal val upperLeftTextValue: Spanned? = upperLeftText?.let(::getTextAsSpanned)
    internal val lowerLeftTextValue: Spanned? = lowerLeftText?.let(::getTextAsSpanned)
    internal val middleTextValue: Spanned? = middleText?.let(::getTextAsSpanned)
    internal val upperRightTextValue: Spanned? = upperRightText?.let(::getTextAsSpanned)
    internal val lowerRightTextValue: Spanned? = lowerRightText?.let(::getTextAsSpanned)

    private fun getFieldVisibility(fieldValue: CharSequence?) = if (fieldValue == null) View.GONE else View.VISIBLE

    private fun getTextAsSpanned(text: CharSequence): Spanned =
        HtmlCompat.fromHtml(text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}
