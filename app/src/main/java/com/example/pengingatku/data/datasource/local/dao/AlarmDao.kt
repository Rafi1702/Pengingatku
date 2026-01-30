package com.example.pengingatku.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity

@Dao
interface AlarmDao{

    @Query("SELECT * FROM alarm")
   suspend fun getAllAlarm(): List<AlarmEntity>

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: String): AlarmEntity
}