package com.trendyol.uicomponents.ratingbarcompose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.trendyol.ratingbarcompose.R
import kotlin.math.floor

/**
 * A composable function that renders a rating bar.
 *
 * @param modifier The modifier to apply to the rating bar.
 * @param rating The current rating.
 * @param @DrawableRes painterRes The resource id of the drawable to use for the stars.
 * @param emptyColor The color of the empty stars.
 * @param filledColor The color of the filled stars.
 * @param spaceBetween The space between the stars.
 * @param itemCount The number of stars.
 * @param itemSize The size of each star.
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    @DrawableRes painterRes: Int = R.drawable.ic_star,
    emptyColor: Color? = null,
    filledColor: Color,
    spaceBetween: Dp,
    itemCount: Int,
    itemSize: Dp,
) {

    val painter = painterResource(id = painterRes)
    val spacePx: Float = with(LocalDensity.current) { spaceBetween.toPx() }
    val totalWidth: Dp = itemSize * itemCount + spaceBetween * (itemCount - 1)

    Canvas(
        modifier = modifier
            .size(width = totalWidth, height = itemSize)
            .graphicsLayer(alpha = 0.99f)
    ) {
        drawRating(
            itemCount = itemCount,
            painter = painter,
            rating = rating,
            filledColor = filledColor,
            emptyColor = emptyColor,
            space = spacePx
        )
    }
}

private fun DrawScope.drawRating(
    itemCount: Int,
    painter: Painter,
    rating: Float,
    filledColor: Color,
    emptyColor: Color?,
    space: Float,
) {
    val itemWidth = size.height

    repeat(itemCount) { index ->
        val startPos = (itemWidth * index) + (space * index)
        translate(left = startPos) {
            with(painter) {
                draw(
                    size = Size(size.height, size.height),
                    colorFilter = emptyColor?.let(ColorFilter::tint)
                )
            }
        }
    }

    val rectWidth = (rating * itemWidth) + (floor(rating) * space)
    drawRect(
        color = filledColor,
        size = Size(width = rectWidth, height = size.height),
        blendMode = BlendMode.SrcIn,
    )
}