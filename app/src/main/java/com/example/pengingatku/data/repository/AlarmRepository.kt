package com.example.pengingatku.data.repository

import android.content.Context
import android.util.Log
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.datasource.local.AppDatabase
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntity
import com.example.pengingatku.data.datasource.mappers.fromAlarmEntityList
import com.example.pengingatku.data.datasource.mappers.toAlarmEntity
import com.example.pengingatku.utils.StateHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
class AlarmRepository(context: Context, coroutineScope: CoroutineScope) {

    private val db = AppDatabase.getDatabase(context = context)

    val getTimerDatas = db.alarmDao().getAllAlarm().map {
        StateHelper.Success(it.fromAlarmEntityList())
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StateHelper.Loading
    )


    suspend fun addTimer(newAlarmInformation: AlarmInformation) {
        db.alarmDao().insertAlarm(newAlarmInformation.toAlarmEntity())
    }

    suspend fun toggleCheck(alarmId: Uuid, isActive: Boolean){
        db.alarmDao().updateCheckStatus(alarmId.toString(), isActive)
    }

    suspend fun updateAlarm(newAlarmInformation: AlarmInformation){
        db.alarmDao().updateAlarm(newAlarmInformation.toAlarmEntity())
    }

    suspend fun findAlarmById(alarmId: Uuid): AlarmInformation?{
        return db.alarmDao().getAlarmById(alarmId.toString())?.fromAlarmEntity()
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


//    suspend fun deleteTimer(id: Uuid) {
//        val timerState = (timerData.value as? StateHelper.Success)?.data
//
//        timerState?.let { originalTimer ->
//            timerData.emit(StateHelper.Success(originalTimer.filter { it.id != id }))
//        }
//    }


//
//    suspend fun selectedTimer(id: Uuid) {
//        val timerState = (timerData.value as? StateHelper.Success)?.data
//        val updatedTimerData =
//            timerState?.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }
//
//        updatedTimerData?.let {
//            timerData.emit(StateHelper.Success(it))
//        }
//    }

//    @OptIn(ExperimentalUuidApi::class)
//    suspend fun editTimer(timerInformation: AlarmInformation) {
//        val timerState = (timerData.value as? StateHelper.Success)?.data
//
//        timerState?.let { originalTimer ->
//            timerData.emit(StateHelper.Success(originalTimer.map {
//                if (it.id == timerInformation.id) timerInformation else it
//            }))
//        }
//    }

