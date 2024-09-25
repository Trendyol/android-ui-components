package com.trendyol.uicomponents.quantitypicker

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class QuantityShape(
    val shape: Shape = RoundedCornerShape(20),
    val borderWidth: Dp = 2.dp,
    val borderColor: Color = Color.LightGray,
    val backgroundColor: Color = Color.White
)

object QuantityShapeDefaults {
    fun circle(
        borderColor: Color,
        borderWidth: Dp = 1.2.dp
    ) = QuantityShape(
        shape = RoundedCornerShape(50),
        borderWidth = borderWidth,
        borderColor = borderColor
    )
}