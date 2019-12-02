package com.trendyol.uicomponents.dialogs

import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat

data class DialogViewState(
    val title: String?,
    val showCloseButton: Boolean?,
    private val content: CharSequence,
    val showContentAsHtml: Boolean,
    @DrawableRes val contentImage: Int?,
    val leftButtonText: String? = null,
    val rightButtonText: String? = null,
    val listItems: List<Pair<Boolean, CharSequence>>? = null
) {

    fun isLeftButtonVisible(): Boolean = leftButtonText != null

    fun isRightButtonVisible(): Boolean = rightButtonText != null

    fun getContent(): CharSequence = if (showContentAsHtml) {
        HtmlCompat.fromHtml(content.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
    } else {
        content
    }

    fun isContentVisible(): Boolean = content.isNotEmpty()

    fun isContentImageVisible(): Boolean = contentImage != null

    fun isListVisible(): Boolean = !listItems.isNullOrEmpty()
}
