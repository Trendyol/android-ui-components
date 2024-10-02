package com.trendyol.uicomponents.dialogs.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.trendyol.dialog.R

data class DialogListItemViewState(
    val name: CharSequence,
    val showAsHtml: Boolean,
    val selectedItemDrawable: Int?,
    val selectedTextColor: Int?,
    val isChecked: Boolean,
    val showRadioButton: Boolean,
    @FontRes val contentFontFamily: Int?,
) {

    fun getText(): CharSequence =
        if (showAsHtml) {
            HtmlCompat.fromHtml(name.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        } else {
            name
        }

    fun getSelectedItemDrawable(context: Context): Drawable? =
        selectedItemDrawable?.let { ContextCompat.getDrawable(context, it) }

    fun getSelectedItemDrawableVisibility(): Int =
        if (selectedItemDrawable != null && isChecked) View.VISIBLE else View.INVISIBLE

    fun getSelectedTextColor(context: Context) =
        if (selectedTextColor == null || isChecked.not()) {
            ContextCompat.getColor(context, R.color.ui_components_dialogs_primary_text_color)
        } else {
            ContextCompat.getColor(context, selectedTextColor)
        }

    fun getRadioButtonVisibility(): Int = if (showRadioButton) View.VISIBLE else View.GONE

    fun getRadioButtonChecked(): Boolean = isChecked

    fun getFontFamily(context: Context) = contentFontFamily?.let { ResourcesCompat.getFont(context, it) }
}

