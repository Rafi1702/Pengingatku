package com.example.pengingatku.screen.timer_edit.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.pengingatku.LocalModifier

@Composable
fun BottomEditTimerContainer(pickedDaysByUser: List<Day>) {

    val pickedDays = remember {
        mutableStateOf(pickedDaysByUser)
    }
    LazyColumn(Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Setiap ${pickedDays.value.joinToString(separator = ",") { it.name.substring(0,3) }}")
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.CalendarToday, null)
                    }
                }

                Spacer(Modifier.height(8.dp))

                DayPickerChip(pickedDays.value) {
                    pickedDays.value += it
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
                modifier = Modifier.size(24.dp),
                border = if (daySelected.contains(it)) null else BorderStroke(
                    width = 0.dp,
                    color = Color.Transparent
                ),
                onClick = {
                    onPickedDays(it)
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("${it.name.first()}")
            }
        }
    }

}