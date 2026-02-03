package com.example.pengingatku.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pengingatku.utils.Day

@Entity(tableName= "alarm")
data class AlarmEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name") val name: String,

    val time: String,

    @ColumnInfo(name = "active") val isChecked: Boolean,

    @ColumnInfo(name ="picked_days") val pickedDays: List<Day>
)


