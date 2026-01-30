package com.example.pengingatku.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.pengingatku.utils.Day
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName= "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "name") val name: String,

    val time: String,

    @ColumnInfo(name = "active") val isChecked: Boolean,

    @ColumnInfo(name ="picked_days") val pickedDays: List<Day>
)


