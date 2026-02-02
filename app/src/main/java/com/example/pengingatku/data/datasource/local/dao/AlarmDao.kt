package com.example.pengingatku.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao{

    @Query("SELECT * FROM alarm")
   fun getAllAlarm(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: Int): AlarmEntity?


    @Query("UPDATE alarm SET active = :isActive WHERE id = :alarmId")
    suspend fun updateCheckStatus(alarmId:Int, isActive: Boolean)

    @Insert
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Update
    suspend fun updateAlarm(alarm: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarm: AlarmEntity)

}