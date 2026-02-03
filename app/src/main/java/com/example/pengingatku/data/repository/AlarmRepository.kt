package com.example.pengingatku.data.repository

import android.content.Context
import android.util.Log
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.datasource.local.AppDatabase
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntity
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntityList
import com.example.pengingatku.data.datasource.mappers.toAlarmEntity
import com.example.pengingatku.utils.StateHelper
import com.example.pengingatku.worker.AlarmScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar


class AlarmRepository(context: Context, coroutineScope: CoroutineScope) {

    private val db = AppDatabase.getDatabase(context = context)
    private val alarmScheduler = AlarmScheduler(context = context)

    val getTimerDatas = db.alarmDao().getAllAlarm().map {
        StateHelper.Success(it.fromAlarmEntityList())
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StateHelper.Loading
    )


    suspend fun addTimer(newAlarmInformation: AlarmInformation) {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val test = newAlarmInformation.copy(minutes = minute + 6, hours = hour)

        val insertedId = db.alarmDao().insertAlarm(test.copy(isChecked = true).toAlarmEntity())

        alarmScheduler.callScheduler(
            newAlarmInformation.copy(id = insertedId)
        )

    }

    suspend fun toggleCheck(alarmId: Long, isActive: Boolean) {
        db.alarmDao().updateCheckStatus(alarmId, isActive)
//        alarmScheduler.cancelScheduler(
//            alarmId = alarmId,
//            isAlarmActive = isActive
//        )
    }

    suspend fun updateAlarm(newAlarmInformation: AlarmInformation) {
        db.alarmDao().updateAlarm(newAlarmInformation.toAlarmEntity())
    }

    suspend fun findAlarmById(alarmId: Long): AlarmInformation? {
        return db.alarmDao().getAlarmById(alarmId)?.fromAlarmEntity()
    }

    suspend fun deleteAlarm(alarmInformation: AlarmInformation) {
        Log.d("REPOSITORY", "PASS ALARM ID: ${alarmInformation.id}")
        return db.alarmDao().deleteAlarm(alarmInformation.toAlarmEntity())
    }


//    init {
//        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
//
//        }
//
//    }


}


//    private val timerData = MutableStateFlow<StateHelper<List<AlarmInformation>>>(
//        StateHelper.Loading
//
//    )

//    val timerFlow = timerData.asStateFlow()


//    suspend fun deleteTimer(id: Int) {
//        val timerState = (timerData.value as? StateHelper.Success)?.data
//
//        timerState?.let { originalTimer ->
//            timerData.emit(StateHelper.Success(originalTimer.filter { it.id != id }))
//        }
//    }


//
//    suspend fun selectedTimer(id: Int) {
//        val timerState = (timerData.value as? StateHelper.Success)?.data
//        val updatedTimerData =
//            timerState?.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }
//
//        updatedTimerData?.let {
//            timerData.emit(StateHelper.Success(it))
//        }
//    }

//    @OptIn(ExperimentalIntApi::class)
//    suspend fun editTimer(timerInformation: AlarmInformation) {
//        val timerState = (timerData.value as? StateHelper.Success)?.data
//
//        timerState?.let { originalTimer ->
//            timerData.emit(StateHelper.Success(originalTimer.map {
//                if (it.id == timerInformation.id) timerInformation else it
//            }))
//        }
//    }

