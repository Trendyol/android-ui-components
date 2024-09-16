package com.trendyol.uicomponents.samplecompose.ui.productcard

import com.trendyol.uicomponents.quantitypicker.QuantityData

data class ProductCardData(
    val id: String = "",
    val title: String,
    val quantityData: QuantityData = QuantityData(),
    val isLoading: Boolean = false,
)
