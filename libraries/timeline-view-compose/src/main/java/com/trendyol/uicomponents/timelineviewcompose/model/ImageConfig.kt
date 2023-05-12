package com.trendyol.uicomponents.timelineviewcompose.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp

@Immutable
data class ImageConfig(
    val imageUrl: String,
    val placeholder: Painter? = null,
    val borderWidth: Dp = TimelineViewDefaults.BorderWidth,
    val size: Dp = TimelineViewDefaults.ItemSize,
    val animation: PointAnimation? = null,
)