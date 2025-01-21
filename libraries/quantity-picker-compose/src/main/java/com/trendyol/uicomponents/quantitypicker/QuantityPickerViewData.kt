package com.trendyol.uicomponents.quantitypicker

import androidx.compose.ui.graphics.Color

data class QuantityPickerViewData(
    val minQuantity: Int = -1,
    val maxQuantity: Int = -1,
    val currentQuantity: Int = 0
) {
    fun isAddButtonEnabled(): Boolean {
        return (isMaxQuantitySet() && currentQuantity >= maxQuantity).not()
    }

    private fun isMaxQuantitySet(): Boolean {
        return maxQuantity != -1
    }

    fun getAddIconColor(
        icons: QuantityIcons,
        currentQuantity: Int
    ): Color {
        val iconColor = if (currentQuantity == 0 && icons.addIconBackgroundColor != null) {
            Color.White
        } else {
            icons.iconColor
        }
        return if (isAddButtonEnabled()) iconColor else icons.disabledColor
    }

    fun getBackgroundColor(
        icons: QuantityIcons
    ): Color = if (currentQuantity == 0) {
        icons.addIconBackgroundColor ?: Color.White
    } else Color.White


    fun isSubtractButtonEnabled(): Boolean {
        return (currentQuantity > minQuantity)
    }

    fun getSubtractButtonColor(iconTintColor: Color, disableTintColor: Color): Color {
        return if (isSubtractButtonEnabled()) iconTintColor else disableTintColor
    }
}