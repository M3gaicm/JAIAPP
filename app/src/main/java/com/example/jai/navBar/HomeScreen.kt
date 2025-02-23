package com.example.jai.navBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(viewModel: GastoViewModel, navController: NavController) {
    val backgroundColor = Color(20, 19, 24)

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(modifier = Modifier.fillMaxSize()) {

            if (viewModel.gastos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay gastos aún", color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.gastos) { gasto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Viaje: ${gasto.nombreViaje}", style = MaterialTheme.typography.titleMedium)
                                Text("Creador: ${gasto.creador}")
                                Text("Participantes: ${gasto.participantes.joinToString(", ")}")
                                Text("Coste: ${gasto.coste}€")
                            }
                        }
                    }
                }
            }
        }

        FloatingMenu(navController)
    }
}

@Composable
fun CenteredContent(backgroundColor: Color, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun FloatingMenu(navController: NavController) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(
                visible = menuExpanded,
                enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
                exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MenuButton(text = "Nuevo Gasto", onClick = {
                        navController.navigate("nuevo_gasto")
                    })
                }
            }

            FloatingActionButton(
                onClick = { menuExpanded = !menuExpanded },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Text(if (menuExpanded) "✕" else "+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}



@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White)
    }
}