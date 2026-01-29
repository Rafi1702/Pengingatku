package com.example.pengingatku.data.repository

import android.util.Log
import com.example.pengingatku.AdverbOfTime
import com.example.pengingatku.Day
import com.example.pengingatku.TimerInformation
import com.example.pengingatku.utils.StateHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
class TimerRepository {
    private val timerData = MutableStateFlow<StateHelper<List<TimerInformation>>>(
        StateHelper.Loading

    )

    val timerFlow = timerData.asStateFlow()


    init {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            getTimerDatas()
        }

    }


    suspend fun deleteTimer(id: Uuid) {
        val timerState = (timerData.value as? StateHelper.Success)?.data

        timerState?.let { originalTimer ->
            timerData.emit(StateHelper.Success(originalTimer.filter { it.id != id }))
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun editTimer(timerInformation: TimerInformation) {
        val timerState = (timerData.value as? StateHelper.Success)?.data

        timerState?.let { originalTimer ->
            timerData.emit(StateHelper.Success(originalTimer.map {
                if (it.id == timerInformation.id) timerInformation else it
            }))
        }
    }


    suspend fun getTimerDatas() {
        delay(3000)
        val timerDatas = listOf(
            TimerInformation(
                label = "Work",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
                pickedDays = listOf(Day.SUNDAY, Day.MONDAY)
            ),

            TimerInformation(
                label = "Work",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
            ),

            TimerInformation(
                label = "Work",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
            ),
            TimerInformation(
                label = "Study",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
                pickedDays = Day.entries
            ),
        )

        try {
            timerData.emit(StateHelper.Success(timerDatas))



            Log.d("REPOSITORY", "SUCCESS $timerDatas")

        } catch (e: Exception) {
            Log.d("REPOSITORY", "FAILED ${e.message}")
            timerData.emit(StateHelper.Failure(e))
        }


    }

    suspend fun findTimerById(timerId: Uuid?): TimerInformation? {
        delay(1500)
        val timerState = (timerData.value as? StateHelper.Success)?.data
        return timerState?.find { it.id == timerId }
    }

    suspend fun addTimer(newTimerInformation: TimerInformation) {
        val timerState = (timerData.value as? StateHelper.Success)?.data

        timerState?.let {
            timerData.emit(StateHelper.Success(it + newTimerInformation))
        }

    }

    suspend fun selectedTimer(id: Uuid) = when (val state = timerData.value) {
        is StateHelper.Success -> {
            val updatedTimerData =
                state.data.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }
            timerData.emit(StateHelper.Success(updatedTimerData))
        }

        is StateHelper.Failure -> {
            val err = state.exception
            timerData.emit(StateHelper.Failure(err))
        }

        else -> {}
    }

}