package com.example.pengingatku.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StopWatchScreen() {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "STOP WATCH",
            style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
    }

}