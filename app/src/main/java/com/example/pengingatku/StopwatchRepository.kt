package com.example.pengingatku

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StopwatchRepository {

    private var job: Job? = null

    private val timeFlow = MutableStateFlow(0L)

    val timeState = timeFlow.asStateFlow()




    fun startStopWatch() {
        if (job?.isActive == true) return
        job = CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            while (true) {
                delay(1000)
                timeFlow.update { it + 1 }
            }
        }
    }

    fun pause() = job?.cancel()


}