package com.example.pengingatku.data.repository

import android.content.Context
import android.util.Log
import com.example.pengingatku.AdverbOfTime
import com.example.pengingatku.utils.Day
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.datasource.local.AppDatabase
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntityList
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
class AlarmRepository(context: Context) {
    private val timerData = MutableStateFlow<StateHelper<List<AlarmInformation>>>(
        StateHelper.Loading

    )

    private val db = AppDatabase.getDatabase(context= context)

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
    suspend fun editTimer(timerInformation: AlarmInformation) {
        val timerState = (timerData.value as? StateHelper.Success)?.data

        timerState?.let { originalTimer ->
            timerData.emit(StateHelper.Success(originalTimer.map {
                if (it.id == timerInformation.id) timerInformation else it
            }))
        }
    }


    suspend fun getTimerDatas() {
        delay(3000)
//        val timerDatas = listOf(
//            TimerInformation(
//                label = "Work",
//                hours = 12,
//                minutes = 3,
//                pickedDays = listOf(Day.SUNDAY, Day.MONDAY)
//            ),
//
//            TimerInformation(
//                label = "Work",
//                hours = 12,
//                minutes = 3,
//            ),
//
//            TimerInformation(
//                label = "Work",
//                hours = 12,
//                minutes = 3,
//            ),
//            TimerInformation(
//                label = "Study",
//                hours = 12,
//                minutes = 3,
//                pickedDays = Day.entries
//            ),
//        )



        try {
            val timerDatas = db.alarmDao().getAllAlarm()
            timerData.emit(StateHelper.Success(timerDatas.fromAlarmEntityList()))

            Log.d("REPOSITORY", "SUCCESS $timerDatas")

        } catch (e: Exception) {
            Log.d("REPOSITORY", "FAILED ${e.message}")
            timerData.emit(StateHelper.Failure(e))
        }


    }

    suspend fun findTimerById(timerId: Uuid?): AlarmInformation? {
        delay(1500)
        val timerState = (timerData.value as? StateHelper.Success)?.data
        return timerState?.find { it.id == timerId }
    }

    suspend fun addTimer(newTimerInformation: AlarmInformation) {
        val timerState = (timerData.value as? StateHelper.Success)?.data

        timerState?.let {
            timerData.emit(StateHelper.Success(it + newTimerInformation))
        }

    }

    suspend fun selectedTimer(id: Uuid) {
        val timerState = (timerData.value as? StateHelper.Success)?.data
        val updatedTimerData =
            timerState?.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }

        updatedTimerData?.let {
            timerData.emit(StateHelper.Success(it))
        }
    }
}