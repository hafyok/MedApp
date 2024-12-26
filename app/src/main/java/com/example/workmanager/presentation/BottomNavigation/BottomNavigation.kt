package com.example.workmanager.presentation.BottomNavigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.workmanager.myUiKit.NormalText
import com.example.workmanager.ui.theme.Black
import com.example.workmanager.ui.theme.MainPurple
import com.example.workmanager.ui.theme.White


@Composable
fun BottomNavigation(
    navController: NavController
) {
    val menuItems = listOf(
        BottomItem.MedicamentScreen,
        BottomItem.StatsScreen
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = White
    ){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        menuItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon"
                    )
                },
                label = {
                    NormalText(text = item.title)
                },
                selectedContentColor = Black,
                unselectedContentColor = MainPurple
            )
        }
    }

}
