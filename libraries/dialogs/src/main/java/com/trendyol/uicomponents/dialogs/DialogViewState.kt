package com.trendyol.uicomponents.dialogs

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.trendyol.dialog.R

data class DialogViewState(
    val title: String?,
    val showCloseButton: Boolean?,
    private val content: CharSequence,
    val showContentAsHtml: Boolean,
    @DrawableRes val contentImage: Int?,
    val leftButtonText: String?,
    val rightButtonText: String?,
    val isListVisible: Boolean,
    val isSearchEnabled: Boolean,
    val isClearSearchButtonVisible: Boolean,
    val searchHint: String,
    val titleBackgroundColor: Int,
    val titleTextColor: Int,
    val titleTextPosition: TextPosition,
    val contentTextPosition: TextPosition
) {

    fun isTitleVisible(): Boolean = title.isNullOrEmpty().not()

    fun isLeftButtonVisible(): Boolean = leftButtonText != null

    fun isRightButtonVisible(): Boolean = rightButtonText != null

    fun getContent(): CharSequence = if (showContentAsHtml) {
        HtmlCompat.fromHtml(content.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
    } else {
        content
    }

    fun isContentVisible(): Boolean = content.isNotEmpty()

    fun isContentImageVisible(): Boolean = contentImage?.takeIf { it != 0 } != null

    fun getTitleBackground(context: Context) = ContextCompat.getColor(context, titleBackgroundColor)

    fun getTitleTextColor(context: Context) =  ContextCompat.getColor(context, titleTextColor)

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
}
