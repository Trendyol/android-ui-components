package com.trendyol.uicomponents.quantitypicker

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun QuantitySubtractIcon(
    icons: QuantityIcons,
    quantityData: QuantityPickerViewData,
    onSubtractClick: (() -> Unit)?,
    currentQuantity: Int
) {
    Crossfade(
        label = "subtract",
        targetState = getSubtractIconPainter(
            subtractIcon = icons.subtractIconResId,
            removeIcon = icons.removeIconResId,
            currentQuantity = currentQuantity
        )
    ) { icon ->
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    enabled = quantityData.isSubtractButtonEnabled(),
                    onClick = { onSubtractClick?.invoke() },
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                )
                .padding(8.dp),
            tint = quantityData.getSubtractButtonColor(icons.iconColor, icons.disabledColor)
        )
    }

}

@Composable
private fun getSubtractIconPainter(
    @DrawableRes subtractIcon: Int,
    @DrawableRes removeIcon: Int?,
    currentQuantity: Int
): Painter {
    return if (currentQuantity <= 1 && removeIcon != null) {
        painterResource(id = removeIcon)
    } else {
        painterResource(id = subtractIcon)
    }
}