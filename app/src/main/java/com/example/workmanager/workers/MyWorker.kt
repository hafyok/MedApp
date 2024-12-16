package com.example.workmanager.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanager.MainActivity
import com.example.workmanager.R
import kotlinx.coroutines.delay
import kotlin.properties.Delegates

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "workmng"
        const val Progress = "Progress"
        private const val delayDuration = 1L
    }
    private val notificationChannelId = "DemoNotificationChannelId"

    fun notification(): Notification {
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)

        return NotificationCompat.Builder(applicationContext, "test").setContentTitle("Test title")
            .setContentText("Test text")
            .setSmallIcon(android.R.drawable.ic_delete)
            .addAction(
                android.R.drawable.ic_delete,
                "cancel",
                intent
            )
            .build()
    }

    suspend fun testWorker() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(): Notification{
        createNotificationChannel()

        /*val notification = notification()
        return ForegroundInfo(7, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)*/
        createNotificationChannel()

        val mainActivityIntent = Intent(
            applicationContext,
            MainActivity::class.java)

        var pendingIntentFlag by Delegates.notNull<Int>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE
        } else {
            pendingIntentFlag = PendingIntent.FLAG_UPDATE_CURRENT
        }

        val mainActivityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            mainActivityIntent,
            pendingIntentFlag)


        return NotificationCompat.Builder(
            applicationContext,
            notificationChannelId
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText("Work Request Done!")
            .setContentIntent(mainActivityPendingIntent)
            .setAutoCancel(true)
            .build()
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "test",
                "WorkManager Notifications",
                NotificationManager.IMPORTANCE_HIGH // Установите HIGH для теста
            )
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.getNotificationChannel("test") == null) {
                notificationManager.createNotificationChannel(channel)
            }
        }
    }*/

    private fun createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                notificationChannelId,
                "DemoWorker",
                NotificationManager.IMPORTANCE_DEFAULT,
            )

            val notificationManager: NotificationManager? =
                getSystemService(
                    applicationContext,
                    NotificationManager::class.java)

            notificationManager?.createNotificationChannel(
                notificationChannel
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            0, createNotification()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        //setForeground(createForegroundInfo())
        Log.d(TAG, "doWork: start")


        val firstUpdate = workDataOf(Progress to 0)
        val lastUpdate = workDataOf(Progress to 100)
        setProgress(firstUpdate)
        delay(delayDuration)
        setProgress(lastUpdate)


        Log.d(TAG, "doWork: end")
        return Result.success()
    }

}
