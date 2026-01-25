package com.example.pengingatku

import android.os.Build
import android.util.Log

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random


class TimerRepository {
    val _timerData = MutableStateFlow<List<TimerInformation>>(
        emptyList()
    )

    val timerData = _timerData.asStateFlow()

    suspend fun deleteTimer(id: Int) {
        val updatedTimerData = _timerData.value.map { it }
        _timerData.emit(updatedTimerData)
    }

    suspend fun getTimerDatas(): List<TimerInformation> {
        delay(500)


        val timerDatas = listOf(
            TimerInformation(
                id = 1,
                label = "Work",
                hours = "12",
                minutes = "03",
                timeAdverb = AdverbOfTime.AM,
                pickedDays = listOf(Day.SUNDAY, Day.MONDAY)
            ),

            TimerInformation(
                id = 2,
                label = "Work",
                hours = "12",
                minutes = "03",
                timeAdverb = AdverbOfTime.AM,
            ),

            TimerInformation(
                id = 3,
                label = "Work",
                hours = "12",
                minutes = "03",
                timeAdverb = AdverbOfTime.AM,
            )
        )



        try {
            _timerData.emit(timerDatas)

            Log.d("REPOSITORY", "SUCCESS $timerDatas")

        } catch (e: Exception) {
            Log.d("REPOSITORY", "FAILED ${e.message}")
        }


        return timerDatas
    }

    suspend fun selectedTimer(id: Int) {
        val updatedTimerData =
            _timerData.value.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }
        _timerData.emit(updatedTimerData)
    }
}