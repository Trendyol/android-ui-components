package com.trendyol.uicomponents.quantitypicker

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object QuantityPickerDefaults {
    val defaultColor = Color(0xFF42AF2A)
    val disabledColor = Color(0xFF999999)
    private val textBackGroundColor = Color(0xFFE1F6DD)

    val quantityPickerShape = QuantityShapeDefaults.circle(defaultColor)

    val quantityTextShape = QuantityShape(
        shape = RoundedCornerShape(50),
        borderWidth = 0.dp,
        borderColor = Color.Unspecified,
        backgroundColor = textBackGroundColor
    )

    val quantityTextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = defaultColor
    )
}