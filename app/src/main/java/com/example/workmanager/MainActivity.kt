package com.example.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.ui.theme.WorkManagerTheme


class MainActivity : ComponentActivity() {
    val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .build()

    val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
        .setConstraints(constraints)
        .addTag("myTask")
        .build()

    val otherWorkRequest = OneTimeWorkRequestBuilder<OtherWorker>()
        .addTag("otherTask")
        .build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WorkManager.getInstance(this)
            .beginWith(workRequest)
            .then(otherWorkRequest)
            .enqueue()

        enableEdgeToEdge()
        setContent {
            WorkManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkManagerTheme {
        Greeting("Android")
    }
}