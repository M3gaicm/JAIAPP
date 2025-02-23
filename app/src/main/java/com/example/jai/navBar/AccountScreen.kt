package com.example.jai.navBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.draw.clip
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun AccountScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val email = currentUser?.email ?: "correo@ejemplo.com"
    val scope = rememberCoroutineScope()

    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
    var fotoPerfil by remember { mutableStateOf("") }
    var isDataLoaded by remember { mutableStateOf(false) }

    if (currentUser != null) {
        LaunchedEffect(currentUser.uid) {
            val usuario = db.collection("usuarios").document(currentUser.uid)

            usuario.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        nombre = documentSnapshot.getString("nombre") ?: "Sin nombre"
                        fotoPerfil = documentSnapshot.getString("fotoPerfil") ?: ""
                    }
                    isDataLoaded = true
                }
                .addOnFailureListener { exception ->
                    println("Error: ${exception.message}")
                    isDataLoaded = true
                }
        }
    }

    var imageUrl = if (isDataLoaded && fotoPerfil.isNotEmpty()) {
        "https://api.dicebear.com/7.x/avataaars/png?seed=$fotoPerfil"
    } else {
        "https://example.com/default-avatar.png"
    }

    var user = User(nombre, email, imageUrl)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF141318)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .background(Color(35, 34, 42))
                .clickable {
                    navController.navigate(MyAppRoute.PHOTOPROFILE)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.photoUrl),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(user.email, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(thickness = 1.dp, color = Color(0xFF800080))
        Spacer(modifier = Modifier.height(20.dp))

        OptionItem("Editar Perfil", Icons.Default.AccountCircle) {}
        HorizontalDivider(
            modifier = Modifier.width(380.dp),
            thickness = 1.dp,
            color = Color(0xFF800080).copy(alpha = 0.3f)
        )

        OptionItem("Notificaciones", Icons.Default.Notifications) {}
        HorizontalDivider(
            modifier = Modifier.width(380.dp),
            thickness = 1.dp,
            color = Color(0xFF800080).copy(alpha = 0.3f)
        )

        OptionItem("Configuración", Icons.Default.Settings) {}
        HorizontalDivider(
            modifier = Modifier.width(380.dp),
            thickness = 1.dp,
            color = Color(0xFF800080).copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                scope.launch {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(MyAppRoute.LOGIN) {
                        popUpTo(MyAppRoute.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión", tint = Color.White)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Cerrar Sesión", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun OptionItem(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF800080))
        Spacer(modifier = Modifier.width(15.dp))
        Text(text, fontSize = 16.sp, color = Color.White, modifier = Modifier.weight(1f))
    }
}

data class User(val name: String, val email: String, val photoUrl: String)
