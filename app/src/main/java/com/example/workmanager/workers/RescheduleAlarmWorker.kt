package com.example.workmanager.workers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.Date

class RescheduleAlarmWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.d("alrmwrk", "doWork: begin")

        val timeInMillis = inputData.getLong("TIME_IN_MILLIS", -1)

        if (timeInMillis > 0) {
            scheduleAlarm(applicationContext, timeInMillis)
        } else {
            Log.e("alrmwrk", "Invalid timeInMillis passed to Worker")
            return Result.failure()
        }

        return Result.success()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleAlarm(context: Context, timeInMillis: Long) {
        Log.d("alrmwrk", "scheduleAlarm: begin")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Log.d("alrmwrk", "Alarm set for: ${Date(timeInMillis)}")

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
        Log.d("alrmwrk", "scheduleAlarm: end")
    }
}