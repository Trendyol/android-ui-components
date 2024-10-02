package com.trendyol.uicomponents.dialogs

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.trendyol.dialog.R

data class DialogViewState(
    val title: CharSequence?,
    private val showCloseButton: Boolean?,
    @ColorInt val closeButtonColor: Int?,
    @DrawableRes val closeButtonDrawable: Int?,
    private val content: CharSequence,
    val showContentAsHtml: Boolean,
    @DrawableRes val contentImage: Int?,
    val leftButtonText: String?,
    val rightButtonText: String?,
    val isListVisible: Boolean,
    val isSearchEnabled: Boolean,
    val isClearSearchButtonVisible: Boolean,
    val searchHint: String,
    @ColorRes val titleBackgroundColor: Int,
    val titleTextColor: Int,
    val titleTextPosition: TextPosition,
    val contentTextPosition: TextPosition,
    val webViewContent: WebViewContent?,
    @FontRes val titleFontFamily: Int?,
    @FontRes val contentFontFamily: Int?,
) {

    fun getTitleVisibility(): Int = if (title.isNullOrEmpty().not()) View.VISIBLE else View.GONE

    fun getContent(): CharSequence = if (showContentAsHtml) {
        HtmlCompat.fromHtml(content.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        content
    }

    @ColorInt
    fun getTitleBackgroundColor(context: Context): Int =
        ContextCompat.getColor(context, titleBackgroundColor)

    fun getTitleTextColor(context: Context) = ContextCompat.getColor(context, titleTextColor)

    fun getTitleFontFamily(context: Context) =
        titleFontFamily?.let { ResourcesCompat.getFont(context, it) }

    fun getContentFontFamily(context: Context) =
        contentFontFamily?.let { ResourcesCompat.getFont(context, it) }

    fun getTitleTextPosition(): Int {
        return when (titleTextPosition) {
            TextPosition.CENTER -> 4
            TextPosition.END -> 3
            else -> 2
        }
    }

    fun getContentTextPosition(): Int {
        return when (contentTextPosition) {
            TextPosition.CENTER -> 4
            TextPosition.END -> 3
            else -> 2
        }
    }

    fun getCloseButtonVisibility(): Int = if (showCloseButton == true) View.VISIBLE else View.GONE

    fun getCloseButtonDrawable(
        context: Context
    ): Drawable? {
        val drawableId = closeButtonDrawable ?: R.drawable.ic_ui_components_dialogs_close
        return ContextCompat.getDrawable(context, drawableId)?.apply {
            closeButtonColor?.let { color -> setTint(color) }
        }
    }

    fun getContentImageVisibility(): Int =
        if (contentImage?.takeIf { it != 0 } != null) View.VISIBLE else View.GONE

    fun getContentTextVisibility(): Int = if (content.isNotEmpty()) View.VISIBLE else View.GONE

    fun getWebViewContentVisibility(): Int = if (webViewContent != null) View.VISIBLE else View.GONE

    fun getContentImageDrawable(context: Context): Drawable? =
        if (contentImage != null) {
            ContextCompat.getDrawable(context, contentImage)
        } else {
            null
        }

    fun isSearchVisible(): Int = if (isSearchEnabled) View.VISIBLE else View.GONE

    fun getClearButtonVisibility(): Int =
        if (isClearSearchButtonVisible) View.VISIBLE else View.GONE

    fun getListVisibility(): Int = if (isListVisible) View.VISIBLE else View.GONE

    fun getLeftButtonVisibility(): Int = if (leftButtonText != null) View.VISIBLE else View.GONE

    fun getRightButtonVisibility(): Int = if (rightButtonText != null) View.VISIBLE else View.GONE
}
