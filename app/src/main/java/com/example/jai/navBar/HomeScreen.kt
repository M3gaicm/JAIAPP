package com.example.jai.navBar

import android.util.Log
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun HomeScreen() {
    val backgroundColor = Color(20, 19, 24)
    val expenses = remember { mutableStateListOf<Expense>() }
    Log.d("HomeScreen", "Recomposed!")

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(expenses) { expense ->
                    ExpenseCard(expense)
                }
            }
            FloatingMenu(onExpenseAdded = { expense ->
                Log.d("ExpenseAdded", "Expense added: $expense")
                expenses.add(expense)
            })
        }
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
fun FloatingMenu(onExpenseAdded: (Expense) -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }

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
                    MenuButton(text = "Agregar Participante", onClick = { showPopup = true })
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

    if (showPopup) {
        ParticipantPopup(onDismiss = { showPopup = false }, onExpenseAdded = { expense ->
            Log.d("ExpenseAdded", "Expense added from popup: $expense")
            onExpenseAdded(expense)
            showPopup = false
        })
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

@Composable
fun ExpenseCard(expense: Expense) {
    Log.d("ExpenseCard", "Rendering expense: $expense")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Participantes: ${expense.participants.joinToString(", ")}", color = Color.White)
        }
    }
}

@Composable
fun ParticipantPopup(onDismiss: () -> Unit, onExpenseAdded: (Expense) -> Unit) {
    var participant by remember { mutableStateOf("") }
    val participants = remember { mutableStateListOf<String>() }

    val keyboardController = LocalSoftwareKeyboardController.current
    // Ocultar el teclado cuando el cuadro de diálogo es mostrado
    LaunchedEffect(key1 = true) {
        keyboardController?.hide()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Participante") },
        text = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = participant,
                        onValueChange = { participant = it },
                        label = { Text("Nombre del Participante") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (participant.isNotBlank()) {
                            participants.add(participant)
                            Log.d("ParticipantAdded", "Added: $participant")
                            participant = ""  // Reset input field
                        }
                    }) {
                        Text("+")
                    }
                }

                LazyColumn(modifier = Modifier.heightIn(min = 50.dp, max = 150.dp)) {
                    items(participants.toList()) { name ->
                        Text(name, color = Color.Black)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (participants.isNotEmpty()) {
                    val newExpense = Expense("Usuario Actual", participants.toList(), "0")
                    Log.d("ExpenseAdded", "New expense created: $newExpense")
                    onExpenseAdded(newExpense)
                }
            }) { Text("Confirmar") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

data class Expense(val creator: String, val participants: List<String>, val amount: String)
