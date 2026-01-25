package com.example.pengingatku.screen.TimerList

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.TimerRepository

import com.example.pengingatku.screen.TimerList.components.TimerCard
import kotlinx.coroutines.launch

@Composable
fun TimerListScreen(
    timerRepository: TimerRepository = TimerRepository(),
//    paddingValues: PaddingValues,
//    onDetailClick: (TimerInformation) -> Unit
    onNavigate: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val times by timerRepository.timerData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        timerRepository.getTimerDatas()
        Log.d("EFFECTS", "LAUNCHED EFFECT IN TIMERLIST")
    }


    if (times.isNotEmpty()) {
        LazyVerticalGrid(
            modifier = LocalModifier.current,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)

        ) {
            items(times,key={it.id}) { data ->
                TimerCard(
                    timerInformation = data,
                    onNavigate = onNavigate,
                    onSelectedTimerInfo = { id ->
                        scope.launch {
                            timerRepository.selectedTimer(id)
                        }
                    }
                )
            }
        }
    }else{
        Text("Data Kosong", modifier = Modifier.fillMaxSize().background(color =MaterialTheme.colorScheme.primary))
    }


}