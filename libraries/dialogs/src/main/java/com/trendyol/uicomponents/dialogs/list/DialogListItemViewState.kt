package com.trendyol.uicomponents.dialogs.list

import androidx.core.text.HtmlCompat

data class DialogListItemViewState(
    val name: CharSequence,
    val showAsHtml: Boolean,
    val isChecked: Boolean
) {

    fun getText(): CharSequence = if (showAsHtml) {
        HtmlCompat.fromHtml(name.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
    } else {
        name
    }
}
