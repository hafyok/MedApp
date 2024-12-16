package com.example.workmanager.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("alrmwrk", "BootReceiver: onRecieve: begin")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED){
            val workRequest = OneTimeWorkRequestBuilder<RescheduleAlarmWorker>().build()
            if (context != null) {
                WorkManager.getInstance(context).enqueue(workRequest)
            }
        }
        Log.d("alrmwrk", "BootReceiver: onRecieve: end")
    }
}