package com.example.pengingatku.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.pengingatku.R
import com.example.pengingatku.utils.ALARM_NOTIFICATION_CHANNEL_ID
import com.example.pengingatku.utils.ALARM_NOTIFICATION_CONTENT_TEXT
import com.example.pengingatku.utils.ALARM_NOTIFICATION_CONTENT_TITLE
import com.example.pengingatku.utils.EXTRA_ALARM_ID
import com.example.pengingatku.utils.buildNotification

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        context.startForegroundService(Intent(context, AlarmService::class.java))
        val alarmId = intent.getIntExtra(EXTRA_ALARM_ID, 0)
        buildNotification(
            context = context,
            notificationName = "Alarm Notification $alarmId",
            channelId = ALARM_NOTIFICATION_CHANNEL_ID,
            notificationId = alarmId,
            requestCode = alarmId,
            importanceNotificationChannelLevel = 5,
            notificationDescription = "Alarm Notification",
            intent = intent,
            notificationCompat = { notificationBuilder ->
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(ALARM_NOTIFICATION_CONTENT_TITLE)
                    .setContentText(ALARM_NOTIFICATION_CONTENT_TEXT)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            },
        )

    }
}


//            val name = "Alarm Notification"
//            val descriptionText = "Saluran untuk pengingat alarm"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//
//            val channel = NotificationChannel("ALARM_CHANNEL_ID", name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//
//            val pendingIntent: PendingIntent =
//                PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
//
//            val builder = NotificationCompat.Builder(context, "ALARM_CHANNEL_ID")
//
//            notificationManager.notify(1, builder.build())

