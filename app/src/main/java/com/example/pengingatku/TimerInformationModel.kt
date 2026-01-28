package com.example.pengingatku

import kotlinx.serialization.Serializable

//


@Serializable
enum class AdverbOfTime {
    AM, PM
}

@Serializable
enum class Day(val isWeekend: Boolean = false){
    SUNDAY(true), MONDAY, TUESDAY, WEDNESDAY, THURSTDAY, FRIDAY, SATURDAY(true)
}


@Serializable
data class TimerInformation(
    val id: Int,
    val label: String,
    val hours: Int,
    val minutes: Int,
    val timeAdverb: AdverbOfTime,
    val isChecked: Boolean = false,
    val pickedDays: List<Day>  =emptyList(),
    val timeAdded: Long = System.currentTimeMillis()
)