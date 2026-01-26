package com.example.pengingatku.screen.stop_watch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pengingatku.screen.stop_watch.components.StopWatchText

@Composable
fun StopWatchScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(48.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        StopWatchText()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            val contentPaddingValues = PaddingValues(horizontal = 48.dp, vertical = 16.dp)
            Button(onClick = {
            }, contentPadding = contentPaddingValues) {
                Text("Start")
            }

            Button(onClick = {
            }, contentPadding = contentPaddingValues, enabled = false) {
                Text("Lap")
            }
        }
    }

}