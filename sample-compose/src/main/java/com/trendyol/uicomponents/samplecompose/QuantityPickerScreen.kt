package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.quantitypicker.QuantityIcons
import com.trendyol.uicomponents.quantitypicker.QuantityPicker
import com.trendyol.uicomponents.quantitypicker.QuantityPickerDefaults
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
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            ProductSliderSection()
            SingleProductSection()
            ProductWeightOptionsSection()
            ProductDetailSection()
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
                    quantityPickerIcons = QuantityIcons.default.copy(
                        addIconBackgroundColor = QuantityPickerDefaults.defaultColor
                    ),
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

@Composable
private fun ProductDetailSection() {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Product Detail")
            Divider()
            QuantityPicker(
                modifier = Modifier
                    .height(42.dp)
                    .width(150.dp),
                quantityData = QuantityPickerViewData(currentQuantity = 2),
                direction = QuantityPickerDirection.HORIZONTAL
            )
        }
    }
}

@Composable
private fun ProductWeightOptionsSection() {
    val coroutineScope = rememberCoroutineScope()
    val products = remember {
        mutableStateListOf(
            ProductCardData("0", "500 gr"),
            ProductCardData("1", "1 kg"),
            ProductCardData("2", "2 kg")
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

    Card(modifier = Modifier.padding(16.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            products.forEach { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = product.title)
                    QuantityPicker(
                        icons = QuantityIcons.default.copy(addIconBackgroundColor = QuantityPickerDefaults.defaultColor),
                        showLoading = product.isLoading,
                        quantityData = product.quantityData,
                        direction = QuantityPickerDirection.HORIZONTAL,
                        onAddClick = { onProductClick(product, 1) },
                        onSubtractClick = { onProductClick(product, -1) }
                    )
                }
            }
        }
    }
}
