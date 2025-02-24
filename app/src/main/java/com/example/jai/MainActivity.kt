package com.example.jai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.jai.ui.theme.JAITheme
import com.example.jai.navBar.*
import com.example.jai.auth.LoginScreen
import com.example.jai.login.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JAITheme {
                val navController = rememberNavController()
                val navigateAction = remember(navController) { MyAppNavigationActions(navController) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedDestination = navBackStackEntry?.destination?.route ?: MyAppRoute.HOME

                MyAppContent(
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateTopLevelDestination = navigateAction::navigateTo
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyAppContent(modifier: Modifier = Modifier, navController: NavHostController, selectedDestination: String, navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()  // Initialize FirebaseAuth
        var route: String = MyAppRoute.LOGIN;
        val viewModel: GastoViewModel = viewModel()

        if (firebaseAuth.currentUser != null) route = MyAppRoute.HOME;

        Scaffold(
            topBar = {
                if (selectedDestination != MyAppRoute.LOGIN && selectedDestination != MyAppRoute.SIGNUP && selectedDestination != MyAppRoute.PHOTOPROFILE) {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        title = { Text(text = "MyApp", color = Color.White) },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(35, 34, 42)),
                        actions = {
                            IconButton(onClick = {navController.navigate(MyAppRoute.PREGUNTAS) }) {

                                Icon(
                                    imageVector = Icons.Default.Info, // Icono de "?"
                                    contentDescription = "Ayuda",
                                    tint = Color.White // Color del icono
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (selectedDestination != MyAppRoute.LOGIN && selectedDestination != MyAppRoute.SIGNUP && selectedDestination != MyAppRoute.PHOTOPROFILE) {
                    MyAppBottomNavigation(selectedDestination, navigateTopLevelDestination)
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination = route,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(MyAppRoute.LOGIN) { LoginScreen(navController) }
                    composable(MyAppRoute.SIGNUP) { SignUpScreen(navController) }
                    composable(MyAppRoute.HOME) { HomeScreen(viewModel, navController) }
                    composable(MyAppRoute.GASTO) { NuevoGastoScreen(navController, viewModel) }
                    composable(MyAppRoute.ACCOUNT) { AccountScreen(navController) }
                    composable(MyAppRoute.NOTIFICATION) { NotificationsScreen()  }
                    composable(MyAppRoute.PHOTOPROFILE) { PhotoProfile(navController)}
                    composable(MyAppRoute.PREGUNTAS) { preguntas()}
                }
            }
        }
    }

    @Composable
    fun MyAppBottomNavigation(
        selectedDestination: String,
        navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
    ) {
        NavigationBar(containerColor = Color(35, 34, 42)) {
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
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF800080))
                )
            }
        }
    }
}
