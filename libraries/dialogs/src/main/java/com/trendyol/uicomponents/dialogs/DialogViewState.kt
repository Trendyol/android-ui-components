package com.trendyol.uicomponents.dialogs

import android.content.Context
import android.view.View
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
            TextPosition.CENTER -> View.TEXT_ALIGNMENT_CENTER
            TextPosition.END -> View.TEXT_ALIGNMENT_TEXT_END
            TextPosition.START -> View.TEXT_ALIGNMENT_TEXT_START
            else -> View.TEXT_ALIGNMENT_INHERIT
        }
    }

    fun getContentTextPosition(): Int {
        return when (contentTextPosition) {
            TextPosition.CENTER -> View.TEXT_ALIGNMENT_CENTER
            TextPosition.END -> View.TEXT_ALIGNMENT_TEXT_END
            TextPosition.START -> View.TEXT_ALIGNMENT_TEXT_START
            else -> View.TEXT_ALIGNMENT_INHERIT
        }
    }
}
