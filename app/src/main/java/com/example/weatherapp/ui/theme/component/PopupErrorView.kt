package com.example.weatherapp.ui.theme.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PopupErrorView(
    message: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = message,
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth(),
    )
}
