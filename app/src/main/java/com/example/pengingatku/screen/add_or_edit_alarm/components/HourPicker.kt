package com.example.pengingatku.screen.add_or_edit_alarm.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


const val CENTER_OFFSET = 2


enum class PickerType(val maxValue: Int) {
    Hour(24),
    Minute(60)
}

@Composable
fun HourPicker(onChangeHourValue: (Int) -> Unit, pickerType: PickerType) {
    Log.d("RECOMPOSE", "HOUR PICKER RECOMPOSED")
    val middleIndex = Int.MAX_VALUE / 2
    val startIndex = middleIndex - (middleIndex % pickerType.maxValue) - (CENTER_OFFSET - 1)

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)

    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)


    //Trigger recomposition jika Item telah visible atau sudah dipick
    val selectedHour = remember {
        derivedStateOf {
            val centerIndex = listState.firstVisibleItemIndex + CENTER_OFFSET
            centerIndex % pickerType.maxValue
        }
    }

    val isScrolling = remember {
        derivedStateOf {
            listState.isScrollInProgress
        }
    }

    LaunchedEffect(isScrolling.value) {
        if (!isScrolling.value) {
            onChangeHourValue(selectedHour.value)
        }
    }


    Log.d("STATE", "IS SCROLLING VALUE: ${isScrolling.value}")

    Log.d("STATE", "SELECTED HOUR: ${selectedHour.value}")

    Box(contentAlignment = Alignment.Center) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            flingBehavior = snapFlingBehavior
        ) {
            items(Int.MAX_VALUE) {
                val hour = it % pickerType.maxValue
                val hourText = if (hour < 10) "0$hour" else "$hour"
                val textColor =
                    if (isScrolling.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

                val notSelectedTextAlphaValue = if (selectedHour.value == it) 1f else .2f
                Text(
                    text = hourText,
                    style = MaterialTheme.typography.displayLarge.copy(textColor.copy(alpha = notSelectedTextAlphaValue))
                )
            }
        }

        Column(Modifier.fillMaxWidth()) {
            val alphaValue = if (isScrolling.value) .4f else 0f

            val modifierContainer = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseOnSurface.copy(alphaValue))

            Log.d("STATE", "ALPHA VALUE OF CONTAINER $alphaValue")

            Box(
                modifierContainer
            )
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Box(
                modifierContainer
            )
        }
    }
}