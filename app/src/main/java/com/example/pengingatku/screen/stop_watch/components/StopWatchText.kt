package com.example.pengingatku.screen.stop_watch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pengingatku.R

@Composable

fun StopWatchText() {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val localTextStyle =
            MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onSurface)
        Text("00", style = localTextStyle)
        Icon(
            painter = painterResource(id = R.drawable.ic_colon_stopwatch),
            null,
            )
        Text("00", style = localTextStyle)
        Icon(
            painter = painterResource(id = R.drawable.ic_colon_stopwatch),
            null,
        )
        Text("00", style = localTextStyle)
    }
}