package com.trendyol.uicomponents.quantitypickerview

import android.graphics.Typeface
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

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
}
