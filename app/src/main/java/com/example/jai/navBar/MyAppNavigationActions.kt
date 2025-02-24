package com.example.jai.navBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jai.R

class MyAppNavigationActions (private val navController: NavHostController)
{
    fun navigateTo(destination: MyAppTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

}

data class MyAppTopLevelDestination(val route : String, val selectedIcon: ImageVector, val iconTextId : Int)

val TOP_LEVEL_DESTINATIONS = listOf(
    MyAppTopLevelDestination(
    route = MyAppRoute.HOME,
    selectedIcon = Icons.Default.Home,
    iconTextId = R.string.home
),
    MyAppTopLevelDestination(
        route = MyAppRoute.NOTIFICATION,
        selectedIcon = Icons.Default.Notifications,
        iconTextId = R.string.settings
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.ACCOUNT,
        selectedIcon = Icons.Default.AccountCircle,
        iconTextId = R.string.account
    )

)

object MyAppRoute
{
    const val LOGIN = "login"
    const val SIGNUP = "signup";
    const val HOME = "home"
    const val ACCOUNT = "account"
    const val NOTIFICATION = "notifications"
    const val GASTO = "nuevo_gasto"
    const val PHOTOPROFILE = "photoprofile"
    const val PREGUNTAS = "preguntas"
}