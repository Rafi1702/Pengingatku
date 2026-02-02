package com.example.pengingatku

import com.example.pengingatku.utils.Day
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



@Serializable
enum class AdverbOfTime {
    AM, PM
}

data class AlarmInformation(
    val label: String,
    val hours: Int,
    val minutes: Int,
    val isChecked: Boolean = false,
    val pickedDays: List<Day>  =emptyList(),
    val id: Int
){
    val getTimeAdverb = if(hours < 12) AdverbOfTime.AM else AdverbOfTime.PM
}
//fun getDefaultAlarmInformation(): AlarmInformation{
//    return
//}


fun AlarmInformation?.getDefaultAlarmInformation() = this?: AlarmInformation(
    label = "Not Available",
    hours = 0,
    minutes = 0,
    pickedDays = emptyList(),
    id = 0
)