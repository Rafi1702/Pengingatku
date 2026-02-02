package com.example.pengingatku.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import com.example.pengingatku.utils.EXTRA_ALARM_ID
import java.util.Locale

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun setScheduler(minutes: Int, hours: Int, alarmId: Int) {
        Log.d("SCHEDULER", "SCHEDULER WITH alarmId: $alarmId")
        val intent = Intent(context, AlarmReceiver::class.java)
            .apply {
                putExtra(EXTRA_ALARM_ID, alarmId)
            }

        val scheduleTime = Calendar.getInstance().apply {
            set(Calendar.MINUTE, minutes)
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.SECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        Log.d(
            "SCHEDULER",
            "Alarm dijadwalkan untuk: ${sdf.format(scheduleTime.time)}, intent extra: ${
                intent.getIntExtra(
                    "alarm_id", 0
                )
            }"
        )

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
            Log.d("SCHEDULER", "ALARM SCHEDULER EXECUTED 2")
        }

        Log.d("SCHEDULER", "SCHEDULER OUT")


    }

    fun cancelScheduler(alarmId: Int, isAlarmActive: Boolean) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmId,
                Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("alarm_id", alarmId)
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}