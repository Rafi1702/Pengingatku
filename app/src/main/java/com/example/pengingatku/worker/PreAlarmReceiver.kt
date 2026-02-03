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
import com.example.pengingatku.utils.EXTRA_PRE_ALARM_ID
import com.example.pengingatku.utils.PRE_ALARM_NOTIFICATION_CHANNEL_ID
import com.example.pengingatku.utils.PRE_ALARM_NOTIFICATION_CONTENT_TEXT
import com.example.pengingatku.utils.PRE_ALARM_NOTIFICATION_CONTENT_TITLE
import com.example.pengingatku.utils.buildNotification

class PreAlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra(EXTRA_PRE_ALARM_ID, 0)
        Log.d("RECEIVER", "PRE ALARM ID: $alarmId")
        buildNotification(
            context = context,
            notificationName = "Pre Alarm Notification $alarmId",
            channelId = PRE_ALARM_NOTIFICATION_CHANNEL_ID,
            notificationId = alarmId.toInt(),
            requestCode = alarmId.toInt(),
            importanceNotificationChannelLevel = 5,
            notificationDescription = "Pre Alarm Notification",
            intent = intent,
            notificationCompat = { notificationBuilder ->
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(PRE_ALARM_NOTIFICATION_CONTENT_TITLE )
                    .setContentText(PRE_ALARM_NOTIFICATION_CONTENT_TEXT)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            },
        )
        Log.d("RECEIVER", "PRE-ALARM RECEIVER FINISHED")
    }

}
