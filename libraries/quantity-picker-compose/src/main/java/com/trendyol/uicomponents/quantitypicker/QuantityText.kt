package com.trendyol.uicomponents.quantitypicker

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun QuantityText(
    quantityData: QuantityPickerViewData,
    shape: QuantityShape,
    style: TextStyle,
    showLoading: Boolean,
    progressColor: Color,
) {
    val text = quantityData.currentQuantity.toString()
    val textStyle = style.copy(
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )

    val textMeasurer = rememberTextMeasurer()
    val heightInPixels = textMeasurer.measure(
        text = text,
        maxLines = 1,
        style = textStyle
    ).size.height
    val heightInDp = with(LocalDensity.current) { heightInPixels.toDp() }

    Crossfade(
        targetState = showLoading,
        label = text
    ) { loading ->
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(heightInDp),
                strokeWidth = 2.dp,
                color = progressColor
            )
        } else {
            Text(
                text = text,
                style = textStyle,
                modifier = Modifier
                    .background(
                        shape.backgroundColor,
                        shape.shape
                    )
                    .size(heightInDp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuantityText() {
    QuantityText(
        shape = QuantityShapeDefaults.circle(borderColor = Color.Green),
        quantityData = QuantityPickerViewData(
            currentQuantity = 2
        ),
        style = Typography().body1.copy(color = Color.Green),
        showLoading = false,
        progressColor = Color.Blue
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuantityTextLoading() {
    QuantityText(
        shape = QuantityShapeDefaults.circle(borderColor = Color.Green),
        quantityData = QuantityPickerViewData(
            currentQuantity = 2
        ),
        style = Typography().body1.copy(color = Color.Green),
        showLoading = true,
        progressColor = Color.Blue
    )
}