package com.trendyol.uicomponents.fitoptionmessageviewcompose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.trendyol.fitoptionmessageviewcompose.R

@Composable
fun FitOptionMessageView(
    modifier: Modifier,
    @DrawableRes imageResourceId: Int,
    cornerRadius: Int = 16,
    circleSize: Int = 36,
    cradleMargin: Float = 2f,
    textPadding: Float = 4f,
    enableRevealAnimation: Boolean = false,
    revealAnimationDuration: Long = 0
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = imageResourceId),
            modifier = Modifier
                .size(circleSize.dp)
                .clip(CircleShape)
                .align(Alignment.CenterStart)
                .zIndex(1f),
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .height(IntrinsicSize.Min)
                .align(Alignment.CenterStart)
                .padding(start = (circleSize / 2).dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(cornerRadius.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.Transparent,
                    radius = (circleSize / 2f + cradleMargin).dp.toPx(),
                    center = Offset(0f, size.height / 2f),
                    blendMode = BlendMode.Clear,
                )
            }
            Text(
                text = "Kullanıcıların çoğu kendi bedeninizi almanızı öneriyor.Kullanıcıların çoğu kendi bedeninizi almanızı öneriyor., ",
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(
                        start = (circleSize / 2f + cradleMargin + textPadding).dp,
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 8.dp
                    )
                    .align(Alignment.CenterStart),
            )
        }
    }
}

@Preview
@Composable
fun FitOptionMessageViewPreview() {
    MaterialTheme {
        FitOptionMessageView(Modifier, R.drawable.ic_launcher_background)
    }
}
