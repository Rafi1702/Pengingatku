package com.example.pengingatku.utils


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarNavigationScreen( val route: String,val icon: ImageVector){
    TIMER_LIST("Timer",  Icons.Default.Info),
    STOP_WATCH("Watch", Icons.Default.AccessAlarm),
}


sealed class BaseNavigationScreen(val route: String){
    object AddTimer : BaseNavigationScreen("add_timer")
    object EditTimer : BaseNavigationScreen("edit_timer")
}



