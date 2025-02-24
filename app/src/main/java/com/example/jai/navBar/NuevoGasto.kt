package com.example.jai.navBar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.input.VisualTransformation
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoGastoScreen(navController: NavController, viewModel: GastoViewModel) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var nombreViaje by remember { mutableStateOf("") }
    var creador  by remember { mutableStateOf("") }
    var nuevoParticipante by remember { mutableStateOf("") }
    var participantes by remember { mutableStateOf(listOf<String>()) }
    var coste by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        creador = document.getString("nombre") ?: "Usuario Desconocido"
                    }
                }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Gasto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombreViaje,
                onValueChange = { nombreViaje = it },
                label = { Text("Nombre del viaje") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = creador,
                onValueChange = {},
                label = { Text("Creador") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            OutlinedTextField(
                value = nuevoParticipante,
                onValueChange = { nuevoParticipante = it },
                label = { Text("Agregar Participante") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        if (nuevoParticipante.isNotBlank()) {
                            participantes = participantes + nuevoParticipante
                            nuevoParticipante = ""
                        }
                    }) {
                        Text("+")
                    }
                }
            )

            LazyColumn {
                items(participantes) { participante ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                participantes = participantes - participante
                            }
                    ) {
                        Text(
                            text = participante,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = coste,
                onValueChange = {
                    if (it.matches(Regex("^\\d*\\.?\\d*\$"))) coste = it
                },
                label = { Text("Coste") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = VisualTransformation.None
            )

            Button(
                onClick = {
                    val nuevoGasto = Gasto(nombreViaje, creador, participantes, coste)
                    viewModel.agregarGasto(nuevoGasto) // Guardar gasto
                    navController.popBackStack() // Volver a Home
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}

