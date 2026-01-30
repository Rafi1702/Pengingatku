package com.example.pengingatku.utils

import com.example.pengingatku.R

//package com.example.pengingatku.utils
//
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccessAlarm
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.ui.graphics.vector.ImageVector
//
//
//
//sealed class ScreenNavigation(val route: String) {
//    enum class BottomBarScreenNavigation(val route: String, val icon: ImageVector) {
//        TIMER_LIST("alarm", Icons.Default.Info),
//        STOP_WATCH("stopwatch", Icons.Default.AccessAlarm),
//    }
//
//    object AddTimer : ScreenNavigation("add_timer")
//    object EditTimer : ScreenNavigation("edit_timer/{timerId}") {
//        fun createRoute(timerId: Int): String {
//            return route.replace("{timerId}", timerId.toString())
//        }
//    }
//}
//
//
//


sealed class ScreenNavigation(val route: String, val title: String, val icon: Int? = null) {
    // Bottom Bar Screens
    object TimerList : ScreenNavigation("alarm", "Alarm",R.drawable.ic_alarm_clock )
    object StopWatch : ScreenNavigation("stopwatch", "Stopwatch",R.drawable.ic_stopwatch)

    // Other Screens
    object AddTimer : ScreenNavigation("add_timer", "Add Timer")
    object EditTimer : ScreenNavigation("edit_timer/{uuidString}", "Edit Timer") {
        fun createRoute(uuidString: String) = "edit_timer/$uuidString"
    }

    companion object {
        // Gabungkan semua screen dalam satu list
        val allScreens = listOf(TimerList, StopWatch, AddTimer, EditTimer)


        //Digunakan pada Ui
        val bottomBarScreen = listOf(TimerList, StopWatch)

        // Fungsi cerdas untuk mencari title berdasarkan route aktif
        fun getTitle(currentRoute: String?): String {
            if (currentRoute == null) return "Pengingatku"
            // Cari screen yang route-nya cocok (menghandle argumen seperti {timerId})
            val screen = allScreens.find {
                it.route == currentRoute || currentRoute.startsWith(it.route.split("/")[0])
            }
            return screen?.title ?: "Pengingatku"
        }
    }
}