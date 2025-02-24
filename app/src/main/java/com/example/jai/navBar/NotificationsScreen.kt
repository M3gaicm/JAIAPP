package com.example.jai.navBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationsScreen() {
    val backgroundColor = Color(20, 19, 24)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Notificaciones",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ejemplo de Notificaciones
        NotificationCard("Pago recibido", "Juan te ha enviado 20 Makeleles para el almuerzo.")
        NotificationCard("Nuevo gasto añadido", "Pedro añadió un gasto de 50 Mauricios en 'Cena'.")
        NotificationCard("Recordatorio de deuda", "Tienes una deuda pendiente de 15 Bakayokos con Ana.")
    }
}

@Composable
fun NotificationCard(title: String, message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(40, 40, 50))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = message, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}

