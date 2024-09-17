package com.trendyol.uicomponents.quantitypicker

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.trendyol.quantitypickercompose.R

data class QuantityIcons(
    @DrawableRes val addIconResId: Int = R.drawable.ic_plus,
    @DrawableRes val subtractIconResId: Int = R.drawable.ic_subtract,
    @DrawableRes val removeIconResId: Int? = R.drawable.ic_trash,
    val iconColor: Color,
    val disabledColor: Color,
) {
    companion object {
        val default = QuantityIcons(
            addIconResId = R.drawable.ic_plus,
            subtractIconResId = R.drawable.ic_subtract,
            removeIconResId = R.drawable.ic_trash,
            iconColor = QuantityPickerDefaults.defaultColor,
            disabledColor = QuantityPickerDefaults.disabledColor
        )
    }
}


