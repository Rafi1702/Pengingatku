package com.example.pengingatku.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import com.example.pengingatku.AlarmInformation
import com.example.pengingatku.utils.EXTRA_ALARM_ID
import com.example.pengingatku.utils.EXTRA_PRE_ALARM_ID

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun callScheduler(alarm: AlarmInformation) {
        val (label, hours, minutes, _, _, alarmId) = alarm
        Log.d("SCHEDULER", "SCHEDULER WITH alarmId: $alarmId")
        setScheduler(
            alarmId = alarmId,
            minutes = minutes,
            hours = hours,
            applyCalendarCallback = { millis ->
                if (millis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }

            },
            intent = Intent(context, AlarmReceiver::class.java)
                .apply {
                    putExtra(EXTRA_ALARM_ID, alarmId)
                },
        )


        setScheduler(
            alarmId = alarmId,
            minutes = minutes - 5,
            hours = hours,
            intent = Intent(context, PreAlarmReceiver::class.java)
                .apply {
                    putExtra(EXTRA_PRE_ALARM_ID, alarmId + 1000)
                }
        )

        Log.d("SCHEDULER", "SCHEDULER CREATED!!")

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

    private fun setScheduler(
        alarmId: Long, minutes: Int, hours: Int,
        applyCalendarCallback: (Calendar.(Long) -> Unit)? = null,
        intent: Intent,
    ) {

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val scheduleTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
        }

        applyCalendarCallback?.invoke(scheduleTime, scheduleTime.timeInMillis)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                scheduleTime.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                scheduleTime.timeInMillis,
                pendingIntent
            )
        }


    }

}