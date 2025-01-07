package com.trendyol.uicomponents.quantitypicker

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun QuantityAddIcon(
    icons: QuantityIcons,
    quantityData: QuantityPickerViewData,
    onAddClick: (() -> Unit)?,
    showLoading: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    var targetBackgroundColor by remember {
        mutableStateOf(icons.addIconBackgroundColor ?: Color.White)
    }
    var iconTintColor by remember { mutableStateOf(Color.White) }
    val animatedBackgroundColor = remember { Animatable(targetBackgroundColor) }
    var lastQuantityCount = remember { mutableStateOf(-1) }


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
        Log.e(
            "XXX",
            "quantity = ${quantityData.currentQuantity} lastQ:${lastQuantityCount.value} isLoading:$showLoading"
        )
        if (lastQuantityCount.value != quantityData.currentQuantity && showLoading.not()) {
            lastQuantityCount.value = quantityData.currentQuantity
            iconTintColor = quantityData.getAddIconColor(icons, quantityData.currentQuantity)
            setTargetBackgroundColor.invoke(quantityData.getBackgroundColor(icons))
        }
    }

    Icon(
        painter = painterResource(id = icons.addIconResId),
        tint = iconTintColor,
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .background(animatedBackgroundColor.value)
            .padding(8.dp)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                enabled = quantityData.isAddButtonEnabled(),
                onClick = {
                    setTargetBackgroundColor.invoke(Color.White)
                    iconTintColor = icons.iconColor
                    onAddClick?.invoke()
                }
            )
    )
}