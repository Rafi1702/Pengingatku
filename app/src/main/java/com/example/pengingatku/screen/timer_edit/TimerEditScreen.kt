package com.example.pengingatku.screen.timer_edit


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pengingatku.AdverbOfTime
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.TimerInformation
import com.example.pengingatku.data.repository.TimerRepository
import com.example.pengingatku.screen.timer_edit.components.BottomEditTimerContainer
import com.example.pengingatku.screen.timer_edit.components.HourPicker
import com.example.pengingatku.screen.timer_edit.components.PickerType
import com.example.pengingatku.utils.StateHelper
import kotlinx.coroutines.launch

@Composable
fun TimerEditScreen(
    onNavigateToTimerList: () -> Unit,
    timerId: Int,
    timerRepository: TimerRepository
) {
    val scope = rememberCoroutineScope()
    val uiState by timerRepository.timerFlow.collectAsStateWithLifecycle()

    val timerInformation = remember(uiState) {
        val initialData = (uiState as? StateHelper.Success)?.data?.find { it.id == timerId }
        initialData ?: TimerInformation(
            id = timerId,
            label = "Not Available",
            hours = 0,
            minutes = 0,
            timeAdverb = AdverbOfTime.AM,
            pickedDays = emptyList()
        )
    }

    val editedTimerInformation = remember(timerInformation.id) {
        mutableStateOf(timerInformation)
    }

    val isValueChanged = remember {
        derivedStateOf {
            editedTimerInformation.value != timerInformation
        }
    }

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                }){
                    Text("Cancel")
                }
            },
            title = { Text("Hapus ${editedTimerInformation.value.label}?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        timerRepository.deleteTimer(editedTimerInformation.value.id)
                    }
                    showDialog.value = false

                    onNavigateToTimerList()
                }) { Text("Delete Immediately") }
            }
        )
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
                timerInformation,
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
            TextButton(modifier = Modifier.weight(1f), enabled = isValueChanged.value, onClick = {
                scope.launch {
                    timerRepository.editTimer(editedTimerInformation.value)
                }

            }) {
                Text("Save")
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