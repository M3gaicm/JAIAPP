package com.example.jai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.jai.ui.theme.JAITheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBarItemDefaults
import com.example.jai.navBar.AccountScreen
import com.example.jai.navBar.HomeScreen
import com.example.jai.navBar.LoginScreen
import com.example.jai.navBar.SettingsScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // JAITheme
                val navController = rememberNavController()
                val navigateAction = remember(navController)
                {
                    MyAppNavigationActions(navController)
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedDestination = navBackStackEntry?.destination?.route ?: MyAppRoute.HOME

                MyAppContent(
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateTopLevelDestination = navigateAction::navigateTo
                )

        }
    }

    @Composable
    fun MyAppContent(modifier: Modifier = Modifier, navController: NavHostController, selectedDestination: String, navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit)
    {
        Row(modifier = modifier.fillMaxSize())
        {
            Column(modifier = Modifier.fillMaxSize())
            {
                NavHost(
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    startDestination = MyAppRoute.LOGIN
                ) {
                    composable(MyAppRoute.LOGIN) {
                        LoginScreen(navController)
                    }
                    composable(MyAppRoute.HOME) {
                        HomeScreen()
                    }
                    composable(MyAppRoute.ACCOUNT) {
                        AccountScreen()
                    }
                    composable(MyAppRoute.SETTINGS) {
                        SettingsScreen()
                    }
                }
                if (selectedDestination != MyAppRoute.LOGIN) {
                    MyAppBottomNavigation(
                        selectedDestination = selectedDestination,
                        navigateTopLevelDestination = navigateTopLevelDestination
                    )
                }
            }
        }
    }

    @Composable
    fun MyAppBottomNavigation(
        selectedDestination: String,
        navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
    ) {
        NavigationBar {
            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                NavigationBarItem(
                    selected = selectedDestination == destination.route,
                    onClick = { navigateTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = stringResource(id = destination.iconTextId)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color(0xFF800080) // Color morado
                    )
                )
            }
        }
    }



}