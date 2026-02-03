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

class PreAlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pre Alarm Notification"
            val descriptionText = "Pengingat sebelum 5 menit"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel("PRE_ALARM_CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(context, "PRE_ALARM_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("PRE ALARM NOTIFICATION")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that fires when the user taps the notification.
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            notificationManager.notify(0, builder.build())


            Log.d("RECEIVER", "PRE_ALARM NOTIFICATION CHANNEL CREATED")
        }
    }

}