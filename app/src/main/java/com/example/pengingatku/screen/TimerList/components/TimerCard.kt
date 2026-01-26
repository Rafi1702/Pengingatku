package com.example.pengingatku.screen.TimerList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.pengingatku.TimerInformation


@Composable
fun TimerCard(
    timerInformation: TimerInformation,
    onSelectedTimerInfo: (Int) -> Unit,
    onNavigate: () -> Unit
) {
    val (id, label, hours, _, timeAdverb, isChecked, pickedDays) = timerInformation

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier
                .background(
                    color = LocalContentColor.current, shape = RoundedCornerShape(CornerSize(16.dp))
                )
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .sizeIn(maxWidth = 120.dp)
                .clickable(onClick = { onNavigate() })
        ) {

            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                Text(label, style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "$hours:$hours",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        timeAdverb.name,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(Modifier.height(8.dp))
                PickedDaysIndicator(pickedDays)
                Spacer(Modifier.height(8.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {

                Switch(
                    checked = isChecked,// Current state
                    onCheckedChange = { _ ->
                        onSelectedTimerInfo(id)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                )


            }


        }
    }

}