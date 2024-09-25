package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.quantitypicker.QuantityPickerDirection
import com.trendyol.uicomponents.quantitypicker.QuantityPickerViewData
import com.trendyol.uicomponents.samplecompose.ui.productcard.ProductCard
import com.trendyol.uicomponents.samplecompose.ui.productcard.ProductCardData
import com.trendyol.uicomponents.samplecompose.ui.theme.Typography
import com.trendyol.uicomponents.samplecompose.ui.theme.UIComponentsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuantityPickerScreen() {
    UIComponentsTheme {
        Column {
            ProductSliderSection()
            SingleProductSection()
        }
    }
}

@Composable
private fun SingleProductSection() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val coroutineScope = rememberCoroutineScope()

        Text(
            text = "Vertical & Horizontal Picker",
            style = Typography.subtitle1
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            // should implement viewmodel for quantity, loading and button clicks in real world
            val product = remember {
                mutableStateOf(
                    ProductCardData(
                        title = "Chocolate",
                        quantityData = QuantityPickerViewData(maxQuantity = 2)
                    )
                )
            }

            val onProductClick: (Int) -> Unit = remember {
                { quantity ->
                    val newQuantity = product.value.quantityData.currentQuantity + quantity
                    product.value = product.value.copy(isLoading = true)
                    coroutineScope.launch {
                        delay(500)
                        product.value = product.value.copy(
                            quantityData = product.value.quantityData.copy(
                                currentQuantity = newQuantity
                            ),
                            isLoading = false
                        )
                    }
                }
            }

            ProductCard(
                productData = product.value,
                modifier = Modifier
                    .padding(
                        end = 14.dp,
                        top = 10.dp
                    ),
                onAddClick = { onProductClick(1) },
                onSubtractClick = { onProductClick(-1) }
            )


            ProductCard(
                productData = product.value,
                direction = QuantityPickerDirection.HORIZONTAL,
                modifier = Modifier.padding(
                    start = 14.dp,
                    top = 10.dp
                ),
                onAddClick = { onProductClick(1) },
                onSubtractClick = { onProductClick(-1) }
            )
        }
    }
}

@Composable
private fun ProductSliderSection() {
    // should implement viewmodel for quantity, loading and button clicks in real world
    val coroutineScope = rememberCoroutineScope()
    val products = remember {
        mutableStateListOf(
            ProductCardData(title = "Chocolate"),
            ProductCardData(title = "Milk"),
            ProductCardData(title = "Potato"),
            ProductCardData(title = "Chocolate"),
            ProductCardData(title = "Milk"),
            ProductCardData(title = "Potato"),
        )
    }

    val onProductClick: (ProductCardData, Int) -> Unit = remember(products) {
        { product: ProductCardData, quantity: Int ->
            val newQuantity = product.quantityData.currentQuantity + quantity
            coroutineScope.launch {
                val index = products.indexOf(product)
                products[index] = product.copy(isLoading = true)
                coroutineScope.launch {
                    delay(500)
                    products[index] = product.copy(
                        isLoading = false,
                        quantityData = product.quantityData.copy(
                            currentQuantity = newQuantity
                        )
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Slider Picker",
            style = Typography.subtitle1
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    productData = product,
                    modifier = Modifier.padding(
                        end = 14.dp,
                        top = 10.dp
                    ),
                    onAddClick = { onProductClick(it, 1) },
                    onSubtractClick = { onProductClick(it, -1) }
                )
            }
        }
    }
}
