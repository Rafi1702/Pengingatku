package com.example.pengingatku.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import com.example.pengingatku.utils.EXTRA_ALARM_ID
import kotlin.time.Duration.Companion.hours

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        context.startForegroundService(Intent(context, AlarmService::class.java))

        Log.d("RECEIVER", "ALARM FIRED at ${System.currentTimeMillis()}, id: ${intent.getIntExtra(EXTRA_ALARM_ID, 0)}")
    }
}