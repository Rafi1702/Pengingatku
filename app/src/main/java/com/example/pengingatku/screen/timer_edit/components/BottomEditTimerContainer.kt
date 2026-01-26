package com.example.pengingatku.screen.timer_edit.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pengingatku.Day
import java.util.Locale.getDefault

@Composable
fun BottomEditTimerContainer(pickedDaysByUser: List<Day>) {

    val pickedDays = remember {
        mutableStateOf(pickedDaysByUser)
    }
    LazyColumn(
        Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .fillMaxHeight(),
    ) {
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val pickedDaysTextDisplay =
                        when {
                            pickedDays.value.isEmpty() -> "Belum Memilih"
                            pickedDays.value.containsAll(Day.entries) -> "Setiap Hari"
                            else -> "Setiap ${pickedDays.value.takeCharacterAndLowerCase()}"
                        }


                    Text(pickedDaysTextDisplay)
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.CalendarToday, null)
                    }
                }

                Spacer(Modifier.height(8.dp))

                DayPickerChip(pickedDays.value) { day ->
                    val days = pickedDays.value
                    if (days.contains(day)) {
                        pickedDays.value -= day
                    } else {
                        pickedDays.value += day
                    }

                }
            }
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