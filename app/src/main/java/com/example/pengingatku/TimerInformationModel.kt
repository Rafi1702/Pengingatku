package com.example.pengingatku

import com.example.pengingatku.utils.Day
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



@Serializable
enum class AdverbOfTime {
    AM, PM
}




@OptIn(ExperimentalUuidApi::class)
data class TimerInformation(
    val label: String,
    val hours: Int,
    val minutes: Int,
    val isChecked: Boolean = false,
    val pickedDays: List<Day>  =emptyList(),
    val id: Uuid = Uuid.random()
){
    val getTimeAdverb = if(hours < 12) AdverbOfTime.AM else AdverbOfTime.PM
}
//fun getDefaultTimerInformation(): TimerInformation{
//    return
//}

@OptIn(ExperimentalUuidApi::class)
fun TimerInformation?.getDefaultTimerInformation() = this?: TimerInformation(
    label = "Not Available",
    hours = 0,
    minutes = 0,
    pickedDays = emptyList()
)