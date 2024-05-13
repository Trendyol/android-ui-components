package com.trendyol.uicomponents.toolbar

import android.text.Spanned
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StyleRes
import androidx.core.text.HtmlCompat

data class ToolbarViewState
constructor(
    val upperStartText: CharSequence? = null,
    val lowerStartText: CharSequence? = null,
    val middleText: CharSequence? = null,
    val upperEndText: CharSequence? = null,
    val lowerEndText: CharSequence? = null,
    @DrawableRes val startImageDrawableResId: Int = R.drawable.trendyol_uicomponents_toolbar_arrow_back,
    @DrawableRes val middleImageDrawableResId: Int = 0,
    @DrawableRes val endImageDrawableResId: Int = 0,
    @StyleRes val upperStartTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val lowerStartTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @StyleRes val middleTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val upperEndTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
    @StyleRes val lowerRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @StyleRes val lowerEndTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @DrawableRes val toolbarBackground: Int = android.R.color.white,
    @Px val upperStartTextMarginStartInPixel: Int? = null,
    @Px val lowerStartTextMarginStartInPixel: Int? = null,
    @Px val upperEndTextMarginEndInPixel: Int? = null,
    @Px val lowerEndTextMarginEndInPixel: Int? = null,
    @Px val endImageDrawableMarginEndInPixel: Int? = null,
    @Px val startImageDrawableMarginStartInPixel: Int? = null,
    @Px val endImageDrawableVerticalMarginInPixel: Int? = null,
    val enableDotPoint: Boolean = false,
    val isUpperEndTextEnabled: Boolean = true,
    val hideStartImage: Boolean = false,
    val startImageContentDescription: String = "",
    val endImageContentDescription: String = "",
) {

    @Deprecated(message = "Use constructor with start - end namings")
    constructor(
        upperLeftText: CharSequence? = null,
        lowerLeftText: CharSequence? = null,
        middleText: CharSequence? = null,
        upperRightText: CharSequence? = null,
        lowerRightText: CharSequence? = null,
        @DrawableRes leftImageDrawableResId: Int = R.drawable.trendyol_uicomponents_toolbar_arrow_back,
        @DrawableRes middleImageDrawableResId: Int = 0,
        @DrawableRes rightImageDrawableResId: Int = 0,
        @StyleRes upperLeftTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
        @StyleRes lowerLeftTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
        @StyleRes middleTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
        @StyleRes upperRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction,
        @StyleRes lowerRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
        @StyleRes upperRightTextDisabledAppearance: Int =
            R.style.Trendyol_UIComponents_Toolbar_Text_UpperAction_Disabled,
        @DrawableRes toolbarBackground: Int = android.R.color.white,
        @Px upperLeftTextMarginStartInPixel: Int? = null,
        @Px lowerLeftTextMarginStartInPixel: Int? = null,
        @Px upperRightTextMarginEndInPixel: Int? = null,
        @Px lowerRightTextMarginEndInPixel: Int? = null,
        @Px rightImageDrawableMarginEndInPixel: Int? = null,
        @Px leftImageDrawableMarginStartInPixel: Int? = null,
        @Px rightImageDrawableVerticalMarginInPixel: Int? = null,
        enableDotPoint: Boolean = false,
        isUpperRightTextEnabled: Boolean = true,
        hideLeftImage: Boolean = false,
        leftImageContentDescription: String = "",
        rightImageContentDescription: String = "",
        ignored: Int = 0 // Fixes conflicting constructor overload
    ) : this(
        upperStartText = upperLeftText,
        lowerStartText = lowerLeftText,
        middleText = middleText,
        upperEndText = upperRightText,
        lowerEndText = lowerRightText,
        startImageDrawableResId = leftImageDrawableResId,
        middleImageDrawableResId = middleImageDrawableResId,
        endImageDrawableResId = rightImageDrawableResId,
        upperStartTextAppearance = upperLeftTextAppearance,
        lowerStartTextAppearance = lowerLeftTextAppearance,
        middleTextAppearance = middleTextAppearance,
        upperEndTextAppearance = upperRightTextAppearance,
        lowerRightTextAppearance = lowerRightTextAppearance,
        lowerEndTextAppearance = upperRightTextDisabledAppearance,
        toolbarBackground = toolbarBackground,
        upperStartTextMarginStartInPixel = upperLeftTextMarginStartInPixel,
        lowerStartTextMarginStartInPixel = lowerLeftTextMarginStartInPixel,
        upperEndTextMarginEndInPixel = upperRightTextMarginEndInPixel,
        lowerEndTextMarginEndInPixel = lowerRightTextMarginEndInPixel,
        endImageDrawableMarginEndInPixel = rightImageDrawableMarginEndInPixel,
        startImageDrawableMarginStartInPixel = leftImageDrawableMarginStartInPixel,
        endImageDrawableVerticalMarginInPixel = rightImageDrawableVerticalMarginInPixel,
        enableDotPoint = enableDotPoint,
        isUpperEndTextEnabled = isUpperRightTextEnabled,
        hideStartImage = hideLeftImage,
        startImageContentDescription = leftImageContentDescription,
        endImageContentDescription = rightImageContentDescription,
    )

    internal val upperStartTextVisibility: Int = getFieldVisibility(upperStartText)
    internal val lowerStartTextVisibility: Int = getFieldVisibility(lowerStartText)
    internal val middleTextVisibility: Int = getFieldVisibility(middleText)
    internal val upperEndTextVisibility: Int = getFieldVisibility(upperEndText)
    internal val lowerEndTextVisibility: Int = getFieldVisibility(lowerEndText)

    internal val upperStartTextValue: Spanned? = upperStartText?.let(::getTextAsSpanned)
    internal val lowerStartTextValue: Spanned? = lowerStartText?.let(::getTextAsSpanned)
    internal val middleTextValue: Spanned? = middleText?.let(::getTextAsSpanned)
    internal val upperEndTextValue: Spanned? = upperEndText?.let(::getTextAsSpanned)
    internal val lowerEndTextValue: Spanned? = lowerEndText?.let(::getTextAsSpanned)

    private fun getFieldVisibility(fieldValue: CharSequence?) = if (fieldValue == null) View.GONE else View.VISIBLE

    private fun getTextAsSpanned(text: CharSequence): Spanned =
        HtmlCompat.fromHtml(text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}
