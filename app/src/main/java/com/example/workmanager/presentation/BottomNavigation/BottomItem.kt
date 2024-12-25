package com.example.workmanager.presentation.BottomNavigation

import com.example.workmanager.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object MedicamentScreen: BottomItem("Medicament Screen", R.drawable.home_bottom, "medicament_screen")
    object StatsScreen: BottomItem("Statistics Screen", R.drawable.stats_bottom, "stats_screen")
}