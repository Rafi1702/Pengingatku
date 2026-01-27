package com.example.pengingatku.utils


enum class TimeUnit {
    HOUR, MINUTE, SECOND
}

fun TimeUnit.timeTextFormat(second: Long): String {

    val formattedTime = when (this) {
        TimeUnit.HOUR -> second / 3600 % 24
        TimeUnit.MINUTE -> second / 60 % 60
        TimeUnit.SECOND -> second % 60
    }

    return String.format("%02d", formattedTime)
}
