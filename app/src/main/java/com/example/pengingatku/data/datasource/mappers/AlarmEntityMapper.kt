package com.example.pengingatku.data.datasource.mappers

import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.datasource.local.entities.AlarmEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
fun AlarmEntity.fromAlarmEntity(): AlarmInformation {
    val time = this.time.split(":")

    val hours = time[0].toInt()
    val minutes = time[1].toInt()

    return AlarmInformation(
        id = Uuid.parse(this.id),
        isChecked = this.isChecked,
        hours = hours,
        minutes = minutes,
        pickedDays = this.pickedDays,
        label = this.name,

        )
}

fun AlarmInformation.toAlarmEntity(){}

fun List<AlarmEntity>.fromAlarmEntityList(): List<AlarmInformation>{
    return this.map{it.fromAlarmEntity()}
}
