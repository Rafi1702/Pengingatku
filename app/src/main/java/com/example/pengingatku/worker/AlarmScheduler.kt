package com.example.pengingatku.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log

class AlarmScheduler(private val context: Context) {


    fun setScheduler(minutes: Int, hours: Int, alarmId: Int) {
        Log.d("SCHEDULER", "SCHEDULER IN")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply{
            putExtra("alarm_id", alarmId)
        }

        val scheduleTime = Calendar.getInstance().apply {
            set(Calendar.MINUTE, minutes)
            set(Calendar.HOUR, hours)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("SCHEDULER", "BUILD VERSION APPROVED")
            if (alarmManager.canScheduleExactAlarms()) {

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    scheduleTime.timeInMillis,
                    pendingIntent
                )
                Log.d("SCHEDULER", "ALARM SCHEDULER EXECUTED")
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                scheduleTime.timeInMillis,
                pendingIntent
            )
            Log.d("SCHEDULER", "ALARM SCHEDULER EXECUTED")
        }

        Log.d("SCHEDULER", "SCHEDULER OUT")


    }
}