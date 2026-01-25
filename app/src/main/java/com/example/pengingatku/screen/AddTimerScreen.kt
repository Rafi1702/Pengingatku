package com.example.pengingatku.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddTimerScreen(toTimerGrids: () -> Unit) {
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "ADD TIMER DATA",
            style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )

        Button(onClick = toTimerGrids) { Text("KELUAR") }
    }

}

