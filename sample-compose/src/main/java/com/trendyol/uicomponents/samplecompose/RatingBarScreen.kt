package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.ratingbarcompose.RatingBar

@Composable
fun RatingBarScreen(
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier.fillMaxSize()) {
        RatingBar(
            rating = 3.7f,
            filledColor = Color.Green,
            spaceBetween = 6.dp,
            itemCount = 8,
            itemSize = 42.dp,
        )
    }
}