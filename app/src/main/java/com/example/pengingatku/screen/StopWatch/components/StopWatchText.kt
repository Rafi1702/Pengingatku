package com.example.pengingatku.screen.StopWatch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.R

@Composable

fun StopWatchText() {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onSurface)

        ) {
            val localTextStyle = LocalTextStyle.current
            Text("00", style = localTextStyle)
            Icon(painter = painterResource(id = R.drawable.ic_colon_stopwatch), null)
            Text("00", style = localTextStyle)
            Icon(painter = painterResource(id = R.drawable.ic_colon_stopwatch), null)
            Text("00", style = localTextStyle)
        }

    }
}