package com.trendyol.uicomponents.toolbar

import android.text.Spanned
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.text.HtmlCompat

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
    @StyleRes val lowerRightTextAppearance: Int = R.style.Trendyol_UIComponents_Toolbar_Text_LowerAction,
    @DrawableRes val toolbarBackground: Int = android.R.color.white,
    val upperLeftTextMarginStartInPixel: Int? = null,
    val lowerLeftTextMarginStartInPixel: Int?= null,
    val upperRightTextMarginEndInPixel: Int?= null,
    val lowerRightTextMarginEndInPixel: Int?= null,
    val rightImageDrawableMarginEndInPixel: Int?= null,
    val leftImageDrawableMarginStartInPixel: Int?= null

) {

    fun getUpperLeftText(): Spanned? = upperLeftText?.let {
        HtmlCompat.fromHtml(it.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun getLowerLeftText(): Spanned? = lowerLeftText?.let {
        HtmlCompat.fromHtml(it.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun getMiddleText(): Spanned? = middleText?.let {
        HtmlCompat.fromHtml(it.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun getUpperRightText(): Spanned? = upperRightText?.let {
        HtmlCompat.fromHtml(it.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun getLowerRightText(): Spanned? = lowerRightText?.let {
        HtmlCompat.fromHtml(it.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun isUpperLeftTextVisible(): Boolean = upperLeftText != null

    fun isLowerLeftTextVisible(): Boolean = lowerLeftText != null

    fun isMiddleTextVisible(): Boolean = middleText != null

    fun isUpperRightTextVisible(): Boolean = upperRightText != null

    fun isLowerRightTextVisible(): Boolean = lowerRightText != null
}
