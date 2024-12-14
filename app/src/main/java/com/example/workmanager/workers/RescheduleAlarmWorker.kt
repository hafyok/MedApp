package com.example.workmanager.workers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class RescheduleAlarmWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        Log.d("alrmwrk", "doWork: begin")

        scheduleAlarm(applicationContext)
        Log.d("alrmwrk", "doWork: end")
        return Result.success()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleAlarm(context: Context) {
        Log.d("alrmwrk", "scheduleAlarm: begin")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // Время для будильника
        // TODO () С этим надо разобраться (но тесты проходят)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2024)
            set(Calendar.MONTH, 11)
            set(Calendar.DAY_OF_MONTH, 13)
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 58)
            set(Calendar.SECOND, 0)
            if (timeInMillis < System.currentTimeMillis()){
                Log.d("alrmwrk", "{$timeInMillis   ${System.currentTimeMillis()}}")

                add(Calendar.DAY_OF_MONTH, 1) // Если время прошло, установка на следующий день
            }
        }
        Log.d("alrmwrk", "Alarm set for: ${calendar.time}")

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Log.d("alrmwrk", "scheduleAlarm: end")
    }
}