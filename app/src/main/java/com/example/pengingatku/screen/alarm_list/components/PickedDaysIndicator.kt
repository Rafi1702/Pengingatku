package com.example.pengingatku.screen.alarm_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pengingatku.utils.Day

@Composable
fun PickedDaysIndicator(daysPicked: List<Day>) {
    val boxSize = 4.dp

    if (daysPicked.containsAll(Day.entries)) {
        Text("Every Day")
    } else {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Day.entries.map {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (it in daysPicked) Box(
                        modifier = Modifier
                            .size(boxSize)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            )
                    ) else Box(modifier = Modifier.size(boxSize))

                    Text(
                        it.name.first().toString(),
                        style = if (it in daysPicked) MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary) else MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Thin
                        )
                    )

                }
            }

        }
    }

}
