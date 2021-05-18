package com.trendyol.uicomponents.dialogs

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

data class DialogViewState(
    val title: String?,
    private val showCloseButton: Boolean?,
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
    val webViewContent: WebViewContent?
) {

    fun getTitleVisibility(): Int = if (title.isNullOrEmpty().not()) View.VISIBLE else View.GONE

    fun getContent(): CharSequence = if (showContentAsHtml) {
        HtmlCompat.fromHtml(content.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        content
    }

    @ColorInt
    fun getTitleBackgroundColor(context: Context): Int = ContextCompat.getColor(context, titleBackgroundColor)

    fun getTitleTextColor(context: Context) = ContextCompat.getColor(context, titleTextColor)

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

    fun getContentImageVisibility(): Int = if (contentImage?.takeIf { it != 0 } != null) View.VISIBLE else View.GONE

    fun getContentTextVisibility(): Int = if (content.isNotEmpty()) View.VISIBLE else View.GONE

    fun getWebViewContentVisibility(): Int = if (webViewContent != null) View.VISIBLE else View.GONE

    fun getContentImageDrawable(context: Context): Drawable? =
        if (contentImage != null) {
            ContextCompat.getDrawable(context, contentImage)
        } else {
            null
        }

    fun isSearchVisible(): Int = if (isSearchEnabled) View.VISIBLE else View.GONE

    fun getClearButtonVisibility(): Int = if (isClearSearchButtonVisible) View.VISIBLE else View.GONE

    fun getListVisibility(): Int = if (isListVisible) View.VISIBLE else View.GONE

    fun getLeftButtonVisibility(): Int = if (leftButtonText != null) View.VISIBLE else View.GONE

    fun getRightButtonVisibility(): Int = if (rightButtonText != null) View.VISIBLE else View.GONE
}
