package com.trendyol.uicomponents.timelineviewcompose

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import com.trendyol.uicomponents.timelineviewcompose.model.PointAnimation
import kotlinx.coroutines.delay

@Stable
internal fun Modifier.scaleAnimation(pointAnimation: PointAnimation?) = composed {
    var shouldStartAnimation by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()
    val pointScale = if (pointAnimation != null && shouldStartAnimation) {
        infiniteTransition.animateFloat(
            initialValue = pointAnimation.initialValue,
            targetValue = pointAnimation.targetValue,
            animationSpec = pointAnimation.animationSpec,
        )
    } else null

    LaunchedEffect(Unit) {
        delay((pointAnimation?.startDelay ?: 0).toLong())
        shouldStartAnimation = true
    }

    graphicsLayer {
        pointScale?.value?.let {
            scaleX = it
            scaleY = it
        }
    }
}