package com.example.workmanager

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.presentation.MainScreen
import com.example.workmanager.ui.theme.WorkManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .build()

    val workRequest = OneTimeWorkRequestBuilder<DemoWorker>()
        .setConstraints(constraints)
        .addTag("myTask")
        .build()

    val otherWorkRequest = OneTimeWorkRequestBuilder<OtherWorker>()
        .addTag("otherTask")
        .build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                "DemoWorker",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
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
