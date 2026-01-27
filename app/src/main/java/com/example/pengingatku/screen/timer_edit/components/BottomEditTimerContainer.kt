package com.example.pengingatku.screen.timer_edit.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pengingatku.Day
import com.example.pengingatku.TimerInformation
import java.util.Locale.getDefault

@Composable
fun BottomEditTimerContainer(
    timerInformation: TimerInformation,
    onTimerInformationChanged: (TimerInformation) -> Unit,
) {

    val newTimerInformation = remember {
        mutableStateOf(timerInformation)
    }


    LaunchedEffect(newTimerInformation.value) {
        onTimerInformationChanged(newTimerInformation.value)
    }



    LazyColumn(
        Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .fillMaxHeight(),

        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        item {
            val pickedDays = newTimerInformation.value.pickedDays
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val pickedDaysTextDisplay =
                    when {
                        pickedDays.isEmpty() -> "Belum Memilih"
                        pickedDays.containsAll(Day.entries) -> "Setiap Hari"
                        else -> "Setiap ${pickedDays.takeCharacterAndLowerCase()}"
                    }


                Text(pickedDaysTextDisplay)
                IconButton(onClick = {

                }) {
                    Icon(Icons.Default.CalendarToday, null)
                }
            }
        }


        item {
            val pickedDays = newTimerInformation.value.pickedDays
            DayPickerChip(pickedDays) { day ->
                if (pickedDays.contains(day)) {
                    newTimerInformation.value =
                        newTimerInformation.value.copy(pickedDays = pickedDays.filterNot {
                            it == day
                        })

                } else {
                    newTimerInformation.value =
                        newTimerInformation.value.copy(pickedDays = pickedDays + day)

                }


            }
        }
        item {
            val label = newTimerInformation.value.label
            TextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                ),
                value = label, onValueChange = {
                    newTimerInformation.value = newTimerInformation.value.copy(label = it)


                })
        }



        items(items = List(3) {
            "Something-$it"
        }, key = { it }) { content ->
            UserPreferenceTile(content)
        }


    }
}

@Composable
fun DayPickerChip(daySelected: List<Day>, onPickedDays: (Day) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Day.entries.map {
            OutlinedButton(
                modifier = Modifier.size(32.dp),
                border = if (daySelected.contains(it)) BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.tertiary
                ) else BorderStroke(
                    width = 0.dp,
                    color = Color.Transparent
                ),
                onClick = {
                    onPickedDays(it)
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "${it.name.first()}",
                    color = if (it.isWeekend) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }

}

fun List<Day>.takeCharacterAndLowerCase() =
    this.sortedBy { it.ordinal }.joinToString(separator = ", ") {
        it.name.first().toString() + it.name.substring(
            1,
            3
        ).lowercase(getDefault())
    }