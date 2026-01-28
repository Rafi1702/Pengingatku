package com.example.pengingatku.utils

import kotlinx.serialization.StringFormat


enum class TimeUnit {
    HOUR, MINUTE, SECOND
}

fun Long.timeTextFormat() = String.format("%02d", this)

fun TimeUnit.timeUnitTextFormat(second: Long): String {
    val formattedTime = when (this) {
        TimeUnit.HOUR -> second / 3600 % 24
        TimeUnit.MINUTE -> second / 60 % 60
        TimeUnit.SECOND -> second % 60
    }
    return formattedTime.timeTextFormat()
}


