package com.example.jai.navBar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.ui.res.vectorResource

class InfoScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            preguntas()
        }
    }
}

@Composable
fun preguntas() {
    val faqs = listOf(
        "¿Qué es SplitPay?" to "SplitPay es una aplicación que te ayuda a gestionar y dividir gastos compartidos entre amigos, familia o compañeros.",
        "¿Cómo crear un nuevo grupo?" to "Pulsa el botón '+' en la pantalla principal, asigna un nombre al grupo e invita a los participantes.",
        "¿Cómo añadir un gasto?" to "Dentro de un grupo, pulsa el botón 'Añadir gasto', ingresa el monto, selecciona quién pagó y entre quiénes se divide el gasto.",
        "¿Cómo se calculan las deudas?" to "La app automáticamente calcula y optimiza las deudas entre los miembros del grupo, mostrando el balance final.",
        "¿Puedo exportar los gastos?" to "Sí, puedes exportar el resumen de gastos y balances en formato PDF o compartirlo directamente desde la aplicación."
    )

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF232228)).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info", tint = Color(0xFFFF8000))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Acerca de SplitPay", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)) // Gris oscuro
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("¿Qué es SplitPay?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("SplitPay es tu solución para gestionar gastos compartidos de manera simple.", color = Color.White.copy(alpha = 0.9f))
            }
        }



        Text("Preguntas Frecuentes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(top = 16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(faqs) { faq ->
                FAQItem(question = faq.first, answer = faq.second)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors()) {
            Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Contáctanos", color = Color.White)
        }
    }
}

@Composable
fun FeatureItem(icon: ImageVector, text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp).background(Color(0xFF2A2931)).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = text, tint = Color(0xFFFF8000))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White)
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)) // Gris oscuro
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(question, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(answer, fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoScreen() {
    preguntas()
}