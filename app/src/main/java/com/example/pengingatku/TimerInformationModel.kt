package com.example.pengingatku
//


//@Serializable
enum class AdverbOfTime {
    AM, PM
}

enum class Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSTDAY, FRIDAY, SATURDAY
}


//@Serializable
data class TimerInformation(
    val id: Int,
    val label: String,
    val hours: String,
    val minutes: String,
    val timeAdverb: AdverbOfTime,
    val isChecked: Boolean = false,
    val pickedDays: List<Day>  =emptyList(),
    val timeAdded: Long = System.currentTimeMillis()
)