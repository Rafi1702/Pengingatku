package com.example.pengingatku.data.datasource.local.type_converter

import androidx.room.TypeConverter
import com.example.pengingatku.utils.Day
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DaysConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromDayList(value: List<Day>?): String? {
        return gson.toJson(value) // Mengubah List ke "[MONDAY, WEDNESDAY]"
    }

    @TypeConverter
    fun toDayList(value: String?): List<Day>? {
        val listType = object : TypeToken<List<Day>>() {}.type
        return gson.fromJson(value, listType)
    }
}
