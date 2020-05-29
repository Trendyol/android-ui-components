package com.trendyol.uicomponents.dialogs.list

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.trendyol.dialog.R

data class DialogListItemViewState(
    val name: CharSequence,
    val showAsHtml: Boolean,
    val selectedItemDrawable: Int?,
    val selectedTextColor: Int?,
    val isChecked: Boolean,
    val showRadioButton: Boolean
) {

    fun getText(): CharSequence = if (showAsHtml) {
        HtmlCompat.fromHtml(name.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
    } else {
        name
    }

    fun getSelectedItemDrawable(context: Context) =
        selectedItemDrawable?.let { ContextCompat.getDrawable(context, it) }

    fun getSelectedItemDrawableVisibility() =
        if (selectedItemDrawable != null && isChecked) View.VISIBLE else View.INVISIBLE

    fun getSelectedTextColor() =
        if (selectedTextColor == null || isChecked.not()) R.color.primary_text_color else selectedTextColor

    fun getRadioButtonVisibility() =
        if (showRadioButton) View.VISIBLE else View.GONE

    fun getRadioButtonChecked() = isChecked
}
