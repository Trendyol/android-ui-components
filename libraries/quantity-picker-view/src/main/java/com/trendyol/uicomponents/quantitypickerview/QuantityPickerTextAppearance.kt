package com.trendyol.uicomponents.quantitypickerview

import androidx.annotation.ColorInt
import androidx.annotation.FontRes

data class QuantityPickerTextAppearance(
    @ColorInt val textColor: Int,
    val textSize: Int,
    val textStyle: Int,
    @FontRes val textFontFamily: Int
)
