package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trendyol.uicomponents.fitoptionmessageviewcompose.FitOptionMessageView
import com.trendyol.uicomponents.samplecompose.ui.theme.UIComponentsTheme

@Composable
fun FitOptionMessageScreen() {

    Column(
        modifier = Modifier
            .background(color = Color.Gray)
            .padding(8.dp)
    ) {

        FitOptionMessageView(
            modifier = Modifier,
            imageResourceId = R.drawable.ic_launcher_background
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FitOptionMessageView(
            modifier = Modifier,
            imageResourceId = R.drawable.ic_launcher_background,
            cornerRadius = 12,
            circleSize = 48,
            cradleMargin = 4f,
            textPadding = 8f,
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FitOptionMessageView(
            modifier = Modifier,
            imageResourceId = R.drawable.ic_launcher_background,
            cornerRadius = 8,
            circleSize = 24,
            cradleMargin = 2f,
            textPadding = 4f,
        )
    }
}

@Preview
@Composable
fun FitOptionMessagePreview() {
    UIComponentsTheme {
        FitOptionMessageScreen()
    }
}