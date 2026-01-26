package com.example.pengingatku.screen.TimerList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pengingatku.LocalModifier
import com.example.pengingatku.TimerRepository
import com.example.pengingatku.screen.TimerList.components.TimerCard
import com.example.pengingatku.utils.StateHelper
import kotlinx.coroutines.launch

@Composable
fun TimerListScreen(
    timerRepository: TimerRepository = TimerRepository(),
    onNavigate: () -> Unit
) {
    val scope = rememberCoroutineScope()


    val uiState by timerRepository.timerData.collectAsStateWithLifecycle()


    LaunchedEffect(Unit){
        timerRepository.getTimerDatas()
    }


    Box(modifier =  LocalModifier.current.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is StateHelper.Loading -> {
                CircularProgressIndicator()
            }

            is StateHelper.Success -> {
                val times = state.data

                if (times.isNotEmpty()) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)

                    ) {
                        items(times, key = { it.id }) { data ->
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
                } else {
                    Text(
                        "Data Kosong",
                    )
                }
            }

            is StateHelper.Failure -> {

            }


        }
    }


}




//
//    val uiState =
//        remember { mutableStateOf<StateHelper<List<TimerInformation>>>(StateHelper.Loading) }

//    LaunchedEffect(Unit) {
//
////        uiState.fold(onSuccess = { data ->
////            uiState.value = StateHelper.Success(data)
////        }, onFailure = { error ->
////            uiState.value = StateHelper.Failure(Exception("Error On Collecting Timers"))
////        })
//        Log.d("EFFECTS", "LAUNCHED EFFECT IN TIMERLIST")
//    }



//    val uiState = remember {
//        timerRepository.timerData
//            .map { result ->
//                result.fold(
//                    onSuccess = { StateHelper.Success(it) },
//                    onFailure = { StateHelper.Failure(Exception("Error getting timers")) }
//                )
//            }
//    }.collectAsStateWithLifecycle(StateHelper.Loading)
