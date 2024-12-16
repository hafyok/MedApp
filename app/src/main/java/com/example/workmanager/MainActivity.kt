package com.example.workmanager

import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.presentation.MainScreen
import com.example.workmanager.ui.theme.WorkManagerTheme
import com.example.workmanager.workers.RescheduleAlarmWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /*val workRequest = PeriodicWorkRequestBuilder<DemoWorker>(
        16, TimeUnit.MINUTES,
        15, TimeUnit.MINUTES
    )
        .setInputData(Data.Builder().putBoolean("isStart", true).build())
        .setInitialDelay(1, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .addTag("demoTask")
        .build()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }


        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2024)
            set(Calendar.MONTH, 11)
            set(Calendar.DAY_OF_MONTH, 15)
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE, 33)

            if (timeInMillis < System.currentTimeMillis()) add (Calendar.DAY_OF_MONTH, 1)
        }

        val inputData = Data.Builder()
            .putLong("TIME_IN_MILLIS", calendar.timeInMillis)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<RescheduleAlarmWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork("AlarmWorker", ExistingWorkPolicy.REPLACE, workRequest)

        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData("AlarmWorker")
            .observe(this) { workInfos ->
                workInfos?.forEach { workInfo ->
                    Log.d("alrmwrk", "Task state: ${workInfo.state}")
                }
            }

        /*Log.d("dmwrk", "before work")
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "DemoWorker",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )

        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData("DemoWorker")
            .observe(this) { workInfos ->
                workInfos?.forEach { workInfo ->
                    Log.d("dmwrk", "Task state: ${workInfo.state}")
                }
            }
        */
        enableEdgeToEdge()
        setContent {
            WorkManagerTheme {
                MainScreen()
            }
        }
    }

}
