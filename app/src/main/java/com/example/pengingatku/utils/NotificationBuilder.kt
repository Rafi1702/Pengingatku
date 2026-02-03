package com.example.pengingatku.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

fun buildNotification(
    context: Context,
    notificationName: String,
    channelId: String,
    notificationId: Int,
    requestCode: Int,
    importanceNotificationChannelLevel: Int,
    notificationDescription: String,
    intent: Intent,
    notificationCompat: (NotificationCompat.Builder) -> NotificationCompat.Builder,

    ) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, notificationName,  importanceNotificationChannelLevel).apply {
            description = notificationDescription
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)


        val builder = notificationCompat.invoke(
            NotificationCompat.Builder(context, channelId).setContentIntent(
                pendingIntent
            )
        )


        notificationManager.notify(notificationId, builder.build())


        Log.d("RECEIVER", "NOTIFICATION CHANNEL CREATED")

    }


}