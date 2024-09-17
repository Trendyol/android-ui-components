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

    fun getAddIconColor(iconTintColor: Color, disableTintColor: Color): Color {
        return if (isAddButtonEnabled()) iconTintColor else disableTintColor
    }

    fun isSubtractButtonEnabled(): Boolean {
        return (currentQuantity > minQuantity)
    }

    fun getSubtractButtonColor(iconTintColor: Color, disableTintColor: Color): Color {
        return if (isSubtractButtonEnabled()) iconTintColor else disableTintColor
    }
}