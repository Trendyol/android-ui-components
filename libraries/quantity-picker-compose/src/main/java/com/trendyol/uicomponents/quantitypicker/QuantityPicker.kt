package com.trendyol.uicomponents.quantitypicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QuantityPicker(
    modifier: Modifier = Modifier,
    quantityData: QuantityPickerViewData,
    showLoading: Boolean = false,
    direction: QuantityPickerDirection = QuantityPickerDirection.VERTICAL,
    textStyle: TextStyle = QuantityPickerDefaults.quantityTextStyle,
    icons: QuantityIcons = QuantityIcons.default,
    quantityPickerShape: QuantityShape = QuantityPickerDefaults.quantityPickerShape,
    quantityTextShape: QuantityShape = QuantityPickerDefaults.quantityTextShape,
    progressColor: Color = QuantityPickerDefaults.defaultColor,
    onAddClick: (() -> Unit)? = null,
    onSubtractClick: (() -> Unit)? = null
) {
    when (direction) {
        QuantityPickerDirection.VERTICAL -> VerticalQuantityPicker(
            modifier = modifier,
            textStyle = textStyle,
            icons = icons,
            quantityShape = quantityPickerShape,
            quantityTextShape = quantityTextShape,
            quantityData = quantityData,
            showLoading = showLoading,
            progressColor = progressColor,
            onAddClick = onAddClick,
            onSubtractClick = onSubtractClick
        )

        QuantityPickerDirection.HORIZONTAL -> HorizontalQuantityPicker(
            modifier = modifier,
            icons = icons,
            textStyle = textStyle,
            quantityShape = quantityPickerShape,
            quantityTextShape = quantityTextShape,
            quantityData = quantityData,
            showLoading = showLoading,
            progressColor = progressColor,
            onAddClick = onAddClick,
            onSubtractClick = onSubtractClick,
        )
    }
}

@Composable
internal fun VerticalQuantityPicker(
    modifier: Modifier,
    textStyle: TextStyle,
    icons: QuantityIcons,
    quantityShape: QuantityShape,
    quantityTextShape: QuantityShape,
    quantityData: QuantityPickerViewData,
    showLoading: Boolean,
    progressColor: Color,
    onAddClick: (() -> Unit)?,
    onSubtractClick: (() -> Unit)?
) {

    Column(
        modifier = modifier
            .background(
                color = quantityShape.backgroundColor,
                shape = quantityShape.shape
            )
            .border(
                width = quantityShape.borderWidth,
                color = quantityShape.borderColor,
                shape = quantityShape.shape
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        QuantityAddIcon(
            icons = icons,
            quantityData = quantityData,
            onAddClick = onAddClick
        )

        AnimatedVisibility(visible = quantityData.currentQuantity > 0 || showLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                QuantityText(
                    quantityData = quantityData,
                    shape = quantityTextShape,
                    style = textStyle,
                    showLoading = showLoading,
                    progressColor = progressColor
                )
                QuantitySubtractIcon(
                    quantityData = quantityData,
                    onSubtractClick = onSubtractClick,
                    currentQuantity = quantityData.currentQuantity,
                    icons = icons
                )
            }
        }
    }
}

@Composable
internal fun HorizontalQuantityPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    icons: QuantityIcons,
    quantityShape: QuantityShape,
    quantityTextShape: QuantityShape,
    quantityData: QuantityPickerViewData,
    showLoading: Boolean,
    progressColor: Color,
    onAddClick: (() -> Unit)?,
    onSubtractClick: (() -> Unit)?
) {

    Row(
        modifier = modifier
            .border(
                width = quantityShape.borderWidth,
                color = quantityShape.borderColor,
                shape = quantityShape.shape
            )
            .background(
                color = quantityShape.backgroundColor,
                shape = quantityShape.shape
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = quantityData.currentQuantity > 0 || showLoading) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                QuantitySubtractIcon(
                    icons = icons,
                    quantityData = quantityData,
                    onSubtractClick = onSubtractClick,
                    currentQuantity = quantityData.currentQuantity
                )
                QuantityText(
                    quantityData = quantityData,
                    shape = quantityTextShape,
                    style = textStyle,
                    showLoading = showLoading,
                    progressColor = progressColor,
                )
            }
        }
        QuantityAddIcon(
            icons = icons,
            quantityData = quantityData,
            onAddClick = onAddClick,
        )
    }
}

@Preview
@Composable
private fun PreviewHorizontalQuantityPicker() {
    QuantityPicker(
        direction = QuantityPickerDirection.HORIZONTAL,
        quantityData = QuantityPickerViewData(currentQuantity = 2)
    )
}

@Preview
@Composable
private fun PreviewVerticalQuantityPicker() {
    QuantityPicker(quantityData = QuantityPickerViewData(currentQuantity = 1))
}

@Preview
@Composable
private fun PreviewLoadingQuantityPicker() {
    QuantityPicker(
        quantityData = QuantityPickerViewData(currentQuantity = 2),
        showLoading = true

    )
}

@Preview
@Composable
private fun PreviewAddButtonDisabledQuantityPicker() {
    QuantityPicker(
        quantityData = QuantityPickerViewData(currentQuantity = 2, maxQuantity = 2),
    )
}

@Preview
@Composable
private fun PreviewSubtractButtonDisabledQuantityPicker() {
    QuantityPicker(
        quantityData = QuantityPickerViewData(currentQuantity = 2, minQuantity = 3),
    )
}