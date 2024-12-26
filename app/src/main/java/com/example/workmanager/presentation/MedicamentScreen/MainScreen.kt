package com.example.workmanager.presentation.MedicamentScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.workmanager.presentation.BottomNavigation.BottomNavigation
import com.example.workmanager.presentation.BottomNavigation.NavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MedicamentViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        NavGraph(navHostController = navController)
    }

}