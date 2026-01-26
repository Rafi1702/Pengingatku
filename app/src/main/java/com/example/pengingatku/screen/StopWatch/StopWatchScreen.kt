package com.example.pengingatku.screen.StopWatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.screen.StopWatch.components.StopWatchText

@Composable
fun StopWatchScreen() {
    Column(modifier = LocalModifier.current.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        StopWatchText()
    }

}