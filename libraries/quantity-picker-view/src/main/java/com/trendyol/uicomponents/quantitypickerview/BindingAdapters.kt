package com.trendyol.uicomponents.quantitypickerview

import android.graphics.Typeface
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat


internal fun AppCompatTextView.setTextAppearance(
    quantityPickerTextAppearance: QuantityPickerTextAppearance
) {
    val style = when (quantityPickerTextAppearance.textStyle) {
        0 -> Typeface.NORMAL
        1 -> Typeface.BOLD
        2 -> Typeface.ITALIC
        else -> throw IllegalArgumentException("no style attribute")
    }
    setTypeface(typeface, style)
    setTextSize(TypedValue.COMPLEX_UNIT_PX, quantityPickerTextAppearance.textSize.toFloat())
    setTextColor(quantityPickerTextAppearance.textColor)

    //or to support all versions use
    if (quantityPickerTextAppearance.textFontFamily != -1 && quantityPickerTextAppearance.textFontFamily != null) {
        val typeface = ResourcesCompat.getFont(context, quantityPickerTextAppearance.textFontFamily)
        setTypeface(typeface)
    }
}
