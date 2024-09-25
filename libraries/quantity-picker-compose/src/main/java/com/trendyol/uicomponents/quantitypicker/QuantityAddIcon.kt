package com.trendyol.uicomponents.quantitypicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun QuantityAddIcon(
    icons: QuantityIcons,
    quantityData: QuantityPickerViewData,
    onAddClick: (() -> Unit)?
) {
    Icon(
        painter = painterResource(id = icons.addIconResId),
        tint = quantityData.getAddIconColor(icons.iconColor, icons.disabledColor),
        contentDescription = null,
        modifier = Modifier
            .clickable(
                enabled = quantityData.isAddButtonEnabled(),
                onClick = { onAddClick?.invoke() },
                interactionSource = MutableInteractionSource(),
                indication = null,
            )
            .padding(8.dp)
    )
}