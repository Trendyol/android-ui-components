package com.trendyol.uicomponents.quantitypicker

import androidx.annotation.DrawableRes
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
import com.trendyol.quantitypickercompose.R

@Composable
fun QuantityPicker(
    modifier: Modifier = Modifier,
    quantityData: QuantityData,
    showLoading: Boolean = false,
    direction: QuantityPickerDirection = QuantityPickerDirection.VERTICAL,
    textStyle: TextStyle = QuantityPickerDefaults.quantityTextStyle,
    @DrawableRes addIconResId: Int = R.drawable.ic_plus,
    @DrawableRes subtractIconResId: Int = R.drawable.ic_subtract,
    @DrawableRes removeIconResId: Int? = R.drawable.ic_trash,
    iconTintColor: Color = QuantityPickerDefaults.defaultColor,
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
            addIconResId = addIconResId,
            subtractIconResId = subtractIconResId,
            removeIconResId = removeIconResId,
            quantityShape = quantityPickerShape,
            quantityTextShape = quantityTextShape,
            quantityData = quantityData,
            showLoading = showLoading,
            progressColor = progressColor,
            onAddClick = onAddClick,
            onSubtractClick = onSubtractClick,
        )

        QuantityPickerDirection.HORIZONTAL -> HorizontalQuantityPicker(
            modifier = modifier,
            textStyle = textStyle,
            addIconResId = addIconResId,
            subtractIconResId = subtractIconResId,
            removeIconResId = removeIconResId,
            quantityShape = quantityPickerShape,
            quantityTextShape = quantityTextShape,
            quantityData = quantityData,
            showLoading = showLoading,
            progressColor = progressColor,
            onAddClick = onAddClick,
            onSubtractClick = onSubtractClick,
            iconTintColor = iconTintColor,
        )
    }
}

@Composable
internal fun VerticalQuantityPicker(
    modifier: Modifier,
    textStyle: TextStyle,
    @DrawableRes addIconResId: Int,
    @DrawableRes subtractIconResId: Int,
    @DrawableRes removeIconResId: Int?,
    iconTintColor: Color = QuantityPickerDefaults.defaultColor,
    quantityShape: QuantityShape,
    quantityTextShape: QuantityShape,
    quantityData: QuantityData,
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
            addIconResId = addIconResId,
            iconTintColor = iconTintColor,
            showLoading = showLoading,
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
                    subtractIconResId = subtractIconResId,
                    showLoading = showLoading,
                    onSubtractClick = onSubtractClick,
                    removeIconResId = removeIconResId,
                    currentQuantity = quantityData.currentQuantity,
                    iconTintColor = iconTintColor
                )
            }
        }
    }
}

@Composable
internal fun HorizontalQuantityPicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    @DrawableRes addIconResId: Int,
    @DrawableRes subtractIconResId: Int,
    @DrawableRes removeIconResId: Int?,
    iconTintColor: Color,
    quantityShape: QuantityShape,
    quantityTextShape: QuantityShape,
    quantityData: QuantityData,
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
                    subtractIconResId = subtractIconResId,
                    showLoading = showLoading,
                    onSubtractClick = onSubtractClick,
                    removeIconResId = removeIconResId,
                    currentQuantity = quantityData.currentQuantity,
                    iconTintColor = iconTintColor
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
            addIconResId = addIconResId,
            showLoading = showLoading,
            onAddClick = onAddClick,
            iconTintColor = iconTintColor
        )
    }
}

@Preview
@Composable
private fun PreviewHorizontalQuantityPicker() {
    QuantityPicker(
        direction = QuantityPickerDirection.HORIZONTAL,
        quantityData = QuantityData(currentQuantity = 2)
    )
}

@Preview
@Composable
private fun PreviewVerticalQuantityPicker() {
    QuantityPicker(quantityData = QuantityData(currentQuantity = 1))
}

@Preview
@Composable
private fun PreviewLoadingQuantityPicker() {
    QuantityPicker(
        quantityData = QuantityData(currentQuantity = 2),
        showLoading = true

    )
}