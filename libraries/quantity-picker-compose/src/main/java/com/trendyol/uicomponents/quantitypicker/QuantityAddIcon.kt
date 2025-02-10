package com.trendyol.uicomponents.quantitypicker

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun QuantityAddIcon(
    icons: QuantityIcons,
    quantityData: QuantityPickerViewData,
    onAddClick: (() -> Unit)?
) {
    val coroutineScope = rememberCoroutineScope()
    var targetBackgroundColor by remember {
        mutableStateOf(quantityData.getBackgroundColor(icons))
    }
    var iconTintColor by remember {
        mutableStateOf(
            quantityData.getAddIconColor(
                icons,
                quantityData.currentQuantity
            )
        )
    }
    val animatedBackgroundColor = remember { Animatable(targetBackgroundColor) }
    val lastQuantityCount = remember { mutableStateOf(quantityData.currentQuantity) }

    val setTargetBackgroundColor: (color: Color) -> Unit = remember {
        { color ->
            if (targetBackgroundColor != color) {
                targetBackgroundColor = color
                coroutineScope.launch {
                    animatedBackgroundColor.animateTo(
                        targetValue = targetBackgroundColor,
                        animationSpec = tween(500)
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = quantityData.currentQuantity) {
        if (lastQuantityCount.value != quantityData.currentQuantity) {
            lastQuantityCount.value = quantityData.currentQuantity
            iconTintColor = quantityData.getAddIconColor(icons, quantityData.currentQuantity)
            setTargetBackgroundColor.invoke(quantityData.getBackgroundColor(icons))
        }
    }
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                enabled = quantityData.isAddButtonEnabled(),
                onClick = {
                    onAddClick?.invoke()
                }
            )) {
        Icon(
            painter = painterResource(id = icons.addIconResId),
            tint = iconTintColor,
            contentDescription = null,
            modifier = Modifier
                .drawBehind {
                    drawCircle(color = animatedBackgroundColor.value)
                }
                .padding(8.dp)
        )
    }
}