package com.example.pengingatku.screen.add_or_edit_alarm


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.data.repository.AlarmRepository
import com.example.pengingatku.getDefaultAlarmInformation
import com.example.pengingatku.screen.add_or_edit_alarm.components.BottomEditTimerContainer
import com.example.pengingatku.screen.add_or_edit_alarm.components.HourPicker
import com.example.pengingatku.screen.add_or_edit_alarm.components.PickerType
import com.example.pengingatku.utils.StateHelper
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun AlarmEditScreen(
    onNavigateToTimerList: () -> Unit,
    timerId: Long?,
) {
    val alarmRepository = koinInject<AlarmRepository>()
    val scope = rememberCoroutineScope()


    val timerInformationState = loadTimerInformation(timerId, alarmRepository)


    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        val originalTimerInformation = (timerInformationState.value as? StateHelper.Success)?.data
        originalTimerInformation?.let {
            AlertDialog(
                onDismissRequest = {},
                dismissButton = {
                    TextButton(onClick = {
                        showDialog.value = false
                    }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Hapus ${originalTimerInformation.label}?") },
                confirmButton = {

                    TextButton(onClick = {
                        scope.launch {
                            alarmRepository.deleteAlarm(originalTimerInformation)
                        }
                        showDialog.value = false

                        onNavigateToTimerList()
                    }) { Text("Delete Immediately") }
                }
            )
        }

    }

    when (val state = timerInformationState.value) {
        is StateHelper.Success -> {
            Log.d("TIMER_EDIT", "${state.data}")
            val editedTimerInformation = remember {
                mutableStateOf(state.data.getDefaultAlarmInformation())
            }

            val isValueChanged = remember {
                derivedStateOf {
                    editedTimerInformation.value != state.data?.copy(id = editedTimerInformation.value.id)

                }
            }

            Column(LocalModifier.current) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5F),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    WeightBox(2f) {
                        HourPicker(pickerType = PickerType.Hour, onChangeHourValue = {
                            editedTimerInformation.value =
                                editedTimerInformation.value.copy(hours = it)
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
                            editedTimerInformation.value =
                                editedTimerInformation.value.copy(minutes = it)
                        })
                    }


                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                            ),
                        ),

                    ) {


                    BottomEditTimerContainer(
                        editedTimerInformation.value,
                        onTimerInformationChanged = { timer ->
                            editedTimerInformation.value = timer
                        }
                    )


                }

                Row(modifier = Modifier.fillMaxWidth()) {

                    timerId?.let{
                        TextButton(modifier = Modifier.weight(1f), onClick = {
                            showDialog.value = true
                        }) {
                            Text("Delete")
                        }
                    }

                    TextButton(
                        modifier = Modifier.weight(1f),
                        enabled = isValueChanged.value,
                        onClick = {
                            scope.launch {
                                if (timerId != null) {
                                    alarmRepository.updateAlarm(editedTimerInformation.value)
                                } else {
                                    alarmRepository.addTimer(editedTimerInformation.value)
                                }
                            }

                            onNavigateToTimerList()

                        }) {
                        Text("Save")
                    }

                }


            }
        }

        is StateHelper.Failure -> {

        }

        StateHelper.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
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

@Composable
fun loadTimerInformation(
    timerId: Long?,
    alarmRepository: AlarmRepository
): State<StateHelper<AlarmInformation?>> {
    return produceState<StateHelper<AlarmInformation?>>(
        initialValue = StateHelper.Loading,
        key1 = timerId,
        key2 = alarmRepository
    ) {
        val state = try {
            if(timerId!=null){
                val data = alarmRepository.findAlarmById(timerId)
                StateHelper.Success(data)
            }else {
                StateHelper.Success(null.getDefaultAlarmInformation())
            }

        } catch (e: Exception) {
            StateHelper.Failure(e)
        }

        value = state
    }
}