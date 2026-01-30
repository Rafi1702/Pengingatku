package com.example.pengingatku.utils

import androidx.compose.ui.text.intl.Locale


enum class TimeUnit {
    HOUR, MINUTE, SECOND
}

fun Int.timeTextFormat() = this.toString().padStart(2, '0')

fun TimeUnit.timeUnitTextFormat(second: Long): String {
    val formattedTime = when (this) {
        TimeUnit.HOUR -> second / 3600 % 24
        TimeUnit.MINUTE -> second / 60 % 60
        TimeUnit.SECOND -> second % 60
    }
    return formattedTime.toInt().timeTextFormat()
}


