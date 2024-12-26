package com.example.workmanager.presentation.BottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.workmanager.presentation.MedicamentScreen.MedicamentScreen
import com.example.workmanager.presentation.StatisticsScreen.StatisticsScreen

@Composable
fun NavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "medicament_screen"){
        composable("medicament_screen"){
            MedicamentScreen()
        }
        composable("stats_screen"){
            StatisticsScreen()
        }
    }
}