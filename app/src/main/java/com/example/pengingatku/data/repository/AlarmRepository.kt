package com.example.pengingatku.data.repository

import android.util.Log
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.datasource.local.AppDatabase
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntity
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntityList
import com.example.pengingatku.data.datasource.mappers.toAlarmEntity
import com.example.pengingatku.utils.StateHelper
import com.example.pengingatku.worker.AlarmScheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar


class AlarmRepository(
    private val appDatabase: AppDatabase,
    private val alarmScheduler: AlarmScheduler,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    val getTimerDatas = appDatabase.alarmDao().getAllAlarm().map {
        StateHelper.Success(it.fromAlarmEntityList())
    }.stateIn(
        scope = CoroutineScope(coroutineDispatcher),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StateHelper.Loading
    )


    suspend fun addTimer(newAlarmInformation: AlarmInformation) {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val test = newAlarmInformation.copy(minutes = minute + 6, hours = hour)

        val insertedId =
            appDatabase.alarmDao().insertAlarm(test.copy(isChecked = true).toAlarmEntity())

        Log.d("REPOSITORY", "INSERTED ALARM ID $insertedId")
        alarmScheduler.callScheduler(
            test.copy(id = insertedId)
        )

    }

    suspend fun toggleCheck(alarmId: Long, isActive: Boolean) {
        appDatabase.alarmDao().updateCheckStatus(alarmId, isActive)
//        alarmScheduler.cancelScheduler(
//            alarmId = alarmId,
//            isAlarmActive = isActive
//        )
    }

    suspend fun updateAlarm(newAlarmInformation: AlarmInformation) {
        appDatabase.alarmDao().updateAlarm(newAlarmInformation.toAlarmEntity())
    }

    suspend fun findAlarmById(alarmId: Long): AlarmInformation? {
        return appDatabase.alarmDao().getAlarmById(alarmId)?.fromAlarmEntity()
    }

    suspend fun deleteAlarm(alarmInformation: AlarmInformation) {
        Log.d("REPOSITORY", "PASS ALARM ID: ${alarmInformation.id}")
        return appDatabase.alarmDao().deleteAlarm(alarmInformation.toAlarmEntity())
    }


}


