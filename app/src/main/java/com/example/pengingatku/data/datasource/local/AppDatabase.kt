package com.example.pengingatku.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pengingatku.data.datasource.local.dao.AlarmDao
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity
import com.example.pengingatku.data.datasource.local.type_converter.DaysConverter
import com.example.pengingatku.data.datasource.local.type_converter.TimeConverter

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(DaysConverter::class, TimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}