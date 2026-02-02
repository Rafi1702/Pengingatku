package com.example.pengingatku.worker

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.annotation.RequiresPermission

class AlarmScheduler(private val context: Context) {

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun setScheduler(minutes: Int, hours: Int, schedulerId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val scheduleTime = Calendar.getInstance().apply{
            set(Calendar.MINUTE, minutes)
            set(Calendar.HOUR, hours)

            if(timeInMillis >= System.currentTimeMillis()){
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            schedulerId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

       alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,scheduleTime.timeInMillis, pendingIntent)




//        val currentHour = System.currentTimeMillis().hours
//        val currentMinute = System.currentTimeMillis().minutes
//
//        if( > currentMinute + currentHour)



    }
}