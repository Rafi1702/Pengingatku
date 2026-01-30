package com.example.pengingatku.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity

@Dao
interface AlarmDao{

    @Query("SELECT * FROM alarm")
    fun getAllAlarm(): List<AlarmEntity>

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    fun getAlarmById(alarmId: String)
}