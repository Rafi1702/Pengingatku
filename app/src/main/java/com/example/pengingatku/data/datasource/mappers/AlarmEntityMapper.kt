package com.example.pengingatku.data.datasource.mappers

import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity
import com.example.pengingatku.utils.timeTextFormat
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


fun AlarmEntity.fromAlarmEntity(): AlarmInformation {
    val time = this.time.split(":")

    val hours = time[0].toInt()
    val minutes = time[1].toInt()

    return AlarmInformation(
        id = this.id,
        isChecked = this.isChecked,
        hours = hours,
        minutes = minutes,
        pickedDays = this.pickedDays,
        label = this.name,

        )
}

fun AlarmInformation.toAlarmEntity(): AlarmEntity {
    val time = "${this.hours.timeTextFormat()}:${this.minutes.timeTextFormat()}"
    return AlarmEntity(
        id = this.id,
        name = this.label,
        time = time,
        isChecked = this.isChecked,
        pickedDays = this.pickedDays,
    )

}

fun List<AlarmEntity>.fromAlarmEntityList(): List<AlarmInformation> {
    return this.map { it.fromAlarmEntity() }
}
