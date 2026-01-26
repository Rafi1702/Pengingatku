package com.example.pengingatku.utils


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector



sealed class ScreenNavigation(val route: String) {
    enum class BottomBarScreenNavigation(val route: String, val icon: ImageVector) {
        TIMER_LIST("Alarm", Icons.Default.Info),
        STOP_WATCH("Stopwatch", Icons.Default.AccessAlarm),
    }

    object AddTimer : ScreenNavigation("Add Timer")
    object EditTimer : ScreenNavigation("edit_timer/{timerId}")
}



