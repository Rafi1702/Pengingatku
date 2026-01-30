package com.example.pengingatku.screen.alarm_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.components.SwitchToggle
import com.example.pengingatku.utils.timeTextFormat
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
@Composable
fun TimerCard(
    timerInformation: AlarmInformation,
    onSelectedTimerInfo: (Uuid) -> Unit,
    onNavigate: () -> Unit
) {
    val (label, hours, minutes, isChecked, pickedDays) = timerInformation

    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(CornerSize(16.dp))
            )
            .clickable(onClick = { onNavigate() })
            .padding(
                start = 32.dp,
                end = 16.dp,
                bottom = 16.dp,
                top = 16.dp

            )

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),

            ) {

            Text(label, style = MaterialTheme.typography.titleMedium)

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    "${hours.timeTextFormat()}:${minutes.timeTextFormat()}",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    timerInformation.getTimeAdverb.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(Modifier.height(8.dp))
            PickedDaysIndicator(pickedDays)
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {

                SwitchToggle(
                    isChecked = isChecked,
                    onCheckedChange = { _ ->
                        onSelectedTimerInfo(timerInformation.id)
                    }
                )


            }


        }
    }


}
