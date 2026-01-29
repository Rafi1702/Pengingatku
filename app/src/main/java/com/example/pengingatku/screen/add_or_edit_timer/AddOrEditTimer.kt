package com.example.pengingatku.screen.add_or_edit_timer


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
import com.example.pengingatku.TimerInformation
import com.example.pengingatku.data.repository.TimerRepository
import com.example.pengingatku.getDefaultTimerInformation
import com.example.pengingatku.screen.add_or_edit_timer.components.BottomEditTimerContainer
import com.example.pengingatku.screen.add_or_edit_timer.components.HourPicker
import com.example.pengingatku.screen.add_or_edit_timer.components.PickerType
import com.example.pengingatku.utils.StateHelper
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TimerEditScreen(
    onNavigateToTimerList: () -> Unit,
    timerId: Uuid?,
    timerRepository: TimerRepository
) {
    val scope = rememberCoroutineScope()


    val timerInformationState = loadTimerInformation(timerId, timerRepository)


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
                            timerRepository.deleteTimer(originalTimerInformation.id)
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
            Log.d("TIMER_EDIT","${state.data}" )
            val editedTimerInformation = remember {
                mutableStateOf(state.data.getDefaultTimerInformation())
            }

            val isValueChanged = remember{
                derivedStateOf {
                    editedTimerInformation.value != state.data.getDefaultTimerInformation()
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
                            editedTimerInformation.value = editedTimerInformation.value.copy(hours = it)
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
                            editedTimerInformation.value = editedTimerInformation.value.copy(minutes = it)
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
                    TextButton(modifier = Modifier.weight(1f), onClick = {
                        showDialog.value = true
                    }) {
                        Text("Delete")
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        enabled = isValueChanged.value ,
                        onClick = {
                            scope.launch {
                                if (timerId != null) {
                                    timerRepository.editTimer(editedTimerInformation.value.copy())
                                } else {
                                    timerRepository.addTimer(editedTimerInformation.value)
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


//    val hour = remember { mutableStateOf(0) }
//    val minute = remember { mutableStateOf(0) }
//    val timerTitle = remember {
//        mutableStateOf("")
//    }
//    LaunchedEffect(Unit){
//
//       val data = when(val state = uiState){
//            is StateHelper.Success ->  state.data.find{it.id == timerId}
//          else -> null
//        }
//
//
//        hour.value = data?.hours ?: 0
//        minute.value = data?.minutes ?: 0
//        timerTitle.value = data?.label ?: "Not Available"
//    }


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

@OptIn(ExperimentalUuidApi::class)
@Composable
fun loadTimerInformation(
    timerId: Uuid?,
    timerRepository: TimerRepository
): State<StateHelper<TimerInformation?>> {
    return produceState<StateHelper<TimerInformation?>>(
        initialValue = StateHelper.Loading,
        key1 = timerId,
        key2 = timerRepository
    ) {
        val state = try {
            val data = timerRepository.findTimerById(timerId)
            StateHelper.Success(data)
        } catch (e: Exception) {
            StateHelper.Failure(e)
        }

        value = state
    }
}