package com.example.pengingatku

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

//


@Serializable
enum class AdverbOfTime {
    AM, PM
}

@Serializable
enum class Day(val isWeekend: Boolean = false){
    SUNDAY(true), MONDAY, TUESDAY, WEDNESDAY, THURSTDAY, FRIDAY, SATURDAY(true)
}


@OptIn(ExperimentalUuidApi::class)
@Serializable
data class TimerInformation(

    val label: String,
    val hours: Int,
    val minutes: Int,
    val timeAdverb: AdverbOfTime,
    val isChecked: Boolean = false,
    val pickedDays: List<Day>  =emptyList(),
    val id: Uuid = Uuid.random()
)
//fun getDefaultTimerInformation(): TimerInformation{
//    return
//}

@OptIn(ExperimentalUuidApi::class)
fun TimerInformation?.getDefaultTimerInformation() = this?: TimerInformation(
    label = "Not Available",
    hours = 0,
    minutes = 0,
    timeAdverb = AdverbOfTime.AM,
    pickedDays = emptyList()
)