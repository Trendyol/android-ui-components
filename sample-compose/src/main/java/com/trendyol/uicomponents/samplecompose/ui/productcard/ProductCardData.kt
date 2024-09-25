package com.trendyol.uicomponents.samplecompose.ui.productcard

import com.trendyol.uicomponents.quantitypicker.QuantityPickerViewData

data class ProductCardData(
    val id: String = "",
    val title: String,
    val quantityData: QuantityPickerViewData = QuantityPickerViewData(),
    val isLoading: Boolean = false,
)
