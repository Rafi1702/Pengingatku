package com.example.pengingatku.screen.timer_edit


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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pengingatku.AdverbOfTime
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.TimerRepository
import com.example.pengingatku.screen.timer_edit.components.BottomEditTimerContainer
import com.example.pengingatku.screen.timer_edit.components.HourPicker
import com.example.pengingatku.screen.timer_edit.components.PickerType
import com.example.pengingatku.utils.StateHelper

@Composable
fun TimerEditScreen(
    onNavigateToTimerList: () -> Unit,
    timerId: Int,
    timerRepository: TimerRepository
) {
    val uiState by timerRepository.timerData.collectAsStateWithLifecycle()
    val hour = remember { mutableStateOf(0) }
    val minute = remember { mutableStateOf(0) }

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

            WeightBox(.5f) {
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

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {

            when (val state = uiState) {
                is StateHelper.Success -> BottomEditTimerContainer(
                    state.data.find { it.id == timerId }?.pickedDays ?: listOf()
                )

                else -> {}
            }


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