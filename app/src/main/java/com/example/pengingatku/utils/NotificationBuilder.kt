package com.example.pengingatku.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

fun buildNotification(
    context: Context,
    notificationName: String,
    notificationId: String,
    notificationManagerId: Int,
    importanceLevel: Int,
    notificationDescription: String,
    intent: Intent,
    notificationCompat: (NotificationCompat.Builder) -> NotificationCompat.Builder,

    ) {

    val channel = NotificationChannel(notificationId, notificationName, importanceLevel).apply {
        description = notificationDescription
    }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)


    val builder = notificationCompat.invoke(
        NotificationCompat.Builder(context, notificationId).setContentIntent(
            pendingIntent
        )
    )


    notificationManager.notify(notificationManagerId, builder.build())


    Log.d("RECEIVER", "NOTIFICATION CHANNEL CREATED")
}