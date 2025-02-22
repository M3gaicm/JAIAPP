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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val email = currentUser?.email ?: "correo@ejemplo.com"

    val imageUrl = "https://api.a0.dev/assets/image?text=professional%20profile%20picture%20minimalist&aspect=1:1&rounded=true"
    val user = User("Usuario Demo", email, imageUrl)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF141318)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = rememberAsyncImagePainter(user.photoUrl),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFF800080), CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(user.email, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFF800080)
        )
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
                    }
                }
            }
            ,
            colors = ButtonDefaults.buttonColors(),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
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
