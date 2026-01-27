package com.example.pengingatku

import android.os.Build
import android.util.Log
import com.example.pengingatku.utils.StateHelper

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random


class TimerRepository {
    private val timerData = MutableStateFlow<StateHelper<List<TimerInformation>>>(
        StateHelper.Loading

    )

    val timerFlow = timerData.asStateFlow()

//    suspend fun deleteTimer(id: Int) {
//        val updatedTimerData = _timerData.value.map { it }
//        _timerData.emit(updatedTimerData)
//    }

    suspend fun editTimer(timerInformation: TimerInformation){
        val timerState = (timerData.value as? StateHelper.Success)?.data

        timerState?.let{ originalTimer ->
            timerData.emit(StateHelper.Success(originalTimer.map{
                if(it.id == timerInformation.id) timerInformation else it
            }))
        }
    }

    suspend fun getTimerDatas() {
        delay(3000)
        val timerDatas = listOf(
            TimerInformation(
                id = 1,
                label = "Work",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
                pickedDays = listOf(Day.SUNDAY, Day.MONDAY)
            ),

            TimerInformation(
                id = 2,
                label = "Work",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
            ),

            TimerInformation(
                id = 3,
                label = "Work",
                hours = 12,
                minutes = 3,
                timeAdverb = AdverbOfTime.AM,
            ),
            TimerInformation(
                id = 4,
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

    suspend fun selectedTimer(id: Int) {
        when(val state = timerData.value){
            is StateHelper.Success ->{
                val updatedTimerData = state.data.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }
                timerData.emit(StateHelper.Success(updatedTimerData))
            }

            is StateHelper.Failure -> {
                val err = state.exception
                timerData.emit(StateHelper.Failure(err))
            }
            else ->{}
        }
    }
}

