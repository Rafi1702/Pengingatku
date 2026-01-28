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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pengingatku.data.repository.StopwatchRepository
import com.example.pengingatku.data.repository.TimerRepository
import com.example.pengingatku.screen.stop_watch.components.StopWatchText
import kotlinx.coroutines.launch

@Composable
fun StopWatchScreen(stopWatchRepository: StopwatchRepository) {
    val uiSecondState by stopWatchRepository.timeState.collectAsStateWithLifecycle()

    val isTimerActive = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(48.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        StopWatchText(uiSecondState)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            val contentPaddingValues = PaddingValues(horizontal = 48.dp, vertical = 16.dp)
            Button(onClick = {
                if (!isTimerActive.value) {
                    stopWatchRepository.startStopWatch()

                } else {
                    stopWatchRepository.pause()
                }

                isTimerActive.value = !isTimerActive.value
            }, contentPadding = contentPaddingValues) {
                Text(if(!isTimerActive.value) "Start" else "Pause")
            }

            Button(onClick = {

            }, contentPadding = contentPaddingValues, enabled = false) {
                Text("Lap")
            }
        }
    }

}