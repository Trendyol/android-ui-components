package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import android.text.Spanned
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat

data class DialogViewState(
    val title: String?,
    val showCloseButton: Boolean?,
    private val content: SpannableString,
    val showContentAsHtml: Boolean,
    @DrawableRes val contentImage: Int?,
    val leftButtonText: String? = null,
    val rightButtonText: String? = null
) {

    fun isLeftButtonVisible(): Boolean = leftButtonText != null

    fun isRightButtonVisible(): Boolean = rightButtonText != null

    fun getContent(): Spanned = if (showContentAsHtml) {
        HtmlCompat.fromHtml(content.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
    } else {
        content
    }
}
