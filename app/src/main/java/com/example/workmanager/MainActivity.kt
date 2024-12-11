package com.example.workmanager

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.presentation.MainScreen
import com.example.workmanager.ui.theme.WorkManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val constraints = Constraints.Builder()
        .build()

    val workRequest = PeriodicWorkRequestBuilder<DemoWorker>(
        16, TimeUnit.MINUTES,
        15, TimeUnit.MINUTES
    )
        .setInputData(Data.Builder().putBoolean("isStart", true).build())
        .setInitialDelay(1, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .addTag("demoTask")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        Log.d("dmwrk", "before work")
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
        /*WorkManager.getInstance(this)
            .beginWith(workRequest)
            .then(otherWorkRequest)
            .enqueue()*/
        enableEdgeToEdge()
        setContent {
            WorkManagerTheme {
                MainScreen()
            }
        }
    }

}
