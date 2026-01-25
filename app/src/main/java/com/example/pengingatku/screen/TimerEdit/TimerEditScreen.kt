package com.example.pengingatku.screen.TimerEdit


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pengingatku.AdverbOfTime
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.screen.TimerEdit.components.HourPicker
import com.example.pengingatku.screen.TimerEdit.components.PickerType

@Composable
fun TimerEditScreen(onNavigateToTimerList: () -> Unit) {

    val hour = remember{mutableStateOf(0)}
    val minute = remember{mutableStateOf(0)}

    Column(LocalModifier.current) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5F),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            WeightBox(2f) {
                HourPicker(pickerType = PickerType.Hour, onChangeHourValue = {
                    hour.value = it
                })
            }

            WeightBox (.5f){
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(MaterialTheme.colorScheme.onSurface, CircleShape)
                )
            }


            WeightBox(2f) {
                HourPicker(pickerType = PickerType.Minute, onChangeHourValue = {
                    minute.value = it
                })
            }


        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            val timeAdverb = if(hour.value > PickerType.Hour.maxValue) AdverbOfTime.AM.name else AdverbOfTime.PM.name

            Text("${hour.value} : ${minute.value} $timeAdverb ")
        }
    }


}

@Composable
fun RowScope.WeightBox(
    weight: Float = 1f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.weight(weight),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}