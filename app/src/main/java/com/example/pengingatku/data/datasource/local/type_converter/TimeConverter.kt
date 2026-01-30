package com.example.pengingatku.data.datasource.local.type_converter

import com.example.pengingatku.utils.timeTextFormat
import com.google.gson.Gson

class TimeConverter{
    private val gson = Gson()

//    fun fromTime()

    fun toTime(hours:Int, minutes:Int): String{
        return "${hours.timeTextFormat()}:${minutes.timeTextFormat()}"
    }

}