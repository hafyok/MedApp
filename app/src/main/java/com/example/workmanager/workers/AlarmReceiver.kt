package com.example.workmanager.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.workmanager.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("alrmwrk", "AlarmReceiver: onRecieve: begin")
        showNotificaiton(context, "Напоминание!", "Время принять лекарство.")
        Log.d("alrmwrk", "AlarmReceiver: onRecieve: end")
    }

    private fun showNotificaiton(context: Context, title: String, message: String) {
        Log.d("alrmwrk", "AlarmReceiver: showNotification: begin")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "medication_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Medication Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // TODO() Внести контент в уведомления
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
        Log.d("alrmwrk", "AlarmReceiver: showNotification: end")
    }
}