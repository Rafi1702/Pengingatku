package com.example.pengingatku.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pengingatku.data.datasource.local.dao.AlarmDao
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity
import com.example.pengingatku.data.datasource.local.type_converter.DaysConverter


@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(DaysConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AlarmApp"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}