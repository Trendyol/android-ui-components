package com.trendyol.uicomponents.timelineviewcompose.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PointShadowConfig(
    val elevation: Int,
    val color: Color,
    val alpha: Float,
    val radius: Float,
)