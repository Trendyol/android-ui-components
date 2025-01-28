package com.trendyol.uicomponents.samplecompose.ui.productcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.quantitypicker.QuantityIcons
import com.trendyol.uicomponents.quantitypicker.QuantityPicker
import com.trendyol.uicomponents.quantitypicker.QuantityPickerDefaults
import com.trendyol.uicomponents.quantitypicker.QuantityPickerDirection
import com.trendyol.uicomponents.sample.compose.R
import com.trendyol.uicomponents.samplecompose.ui.theme.Typography

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    productData: ProductCardData,
    quantityPickerIcons: QuantityIcons = QuantityIcons.default,
    direction: QuantityPickerDirection = QuantityPickerDirection.VERTICAL,
    onAddClick: ((ProductCardData) -> Unit),
    onSubtractClick: ((ProductCardData) -> Unit)
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.TopStart) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = productData.title,
                        modifier = Modifier.size(120.dp)
                    )
                }
                Text(
                    text = productData.title,
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = Typography.subtitle2
                )
            }

            val pickerAlignment = if (direction == QuantityPickerDirection.VERTICAL)
                Alignment.TopEnd else Alignment.TopStart
            QuantityPicker(
                modifier = modifier.align(pickerAlignment),
                icons = quantityPickerIcons.copy(
                    addIconBackgroundColor = QuantityPickerDefaults.defaultColor
                ),
                direction = direction,
                quantityData = productData.quantityData,
                showLoading = productData.isLoading,
                onAddClick = { onAddClick(productData) },
                onSubtractClick = { onSubtractClick(productData) }
            )
        }
    }
}