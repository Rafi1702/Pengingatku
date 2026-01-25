package com.example.pengingatku.components

import android.util.Log
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import com.example.pengingatku.TimerInformation
import com.example.pengingatku.TimerRepository
import kotlinx.coroutines.async


@Composable
fun rememberTimersState(timerRepository: TimerRepository): State<List<TimerInformation>> {
    return produceState(initialValue = listOf()) {
        val timers = async { timerRepository.getTimerDatas() }.await()
        Log.d("STATE", "Timer State $timers")
        value = timers
    }
}


@Composable
fun ExampleTextField() {
    DisposableEffect(Unit) {
        onDispose {  }

    }
    val textFieldState = remember { mutableStateOf("") }

    LaunchedEffect(textFieldState.value) {
        Log.d("STATE", "THIS IS FROM LAUNCHED EFFECT ON TEXT FIELD")
    }

    TextField(value = textFieldState.value, onValueChange = { textFieldState.value = it })
}

fun ExampleComponent(){



}





