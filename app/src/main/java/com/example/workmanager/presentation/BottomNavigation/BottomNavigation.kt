package com.example.workmanager.presentation.BottomNavigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.workmanager.myUiKit.NormalText

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val menuItems = listOf(
        BottomItem.MedicamentScreen,
        BottomItem.StatsScreen
    )
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar() {
        menuItems.forEachIndexed { index, bottomItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(bottomItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomItem.iconId),
                        contentDescription = "Icon"
                    )
                },
                label = {
                    NormalText(text = bottomItem.title)
                },
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {
                selectedIcon
            } else {
                unselectedIcon
            },
            contentDescription = title
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}