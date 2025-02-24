package com.example.jai.navBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PhotoProfile(navController: NavController) {
    val names = listOf(
        "Carlos", "Ana", "Juan", "Maria", "Pedro", "Sofia",
        "Luis", "Elena", "Diego", "Carmen", "Miguel", "Laura",
        "Antonio", "Isabel", "Francisco", "Jose", "Pilar", "Javier",
        "Leonardo", "Monica", "Nicolas", "Olivia", "Pablo", "Rosa",
        "Samuel", "Teresa", "Victor", "Wendy", "Ximena", "Yolanda",
        "Zacarias", "Adriana", "Benjamin", "Camila", "Daniel", "Estefania"
    )

    var selectedAvatar by remember { mutableStateOf("Carlos") }


    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF23222A))
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Seleccionar Avatar", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .background(Color(0x26FFFFFF), RoundedCornerShape(15.dp))
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter("https://api.dicebear.com/7.x/avataaars/png?seed=$selectedAvatar"),
                    contentDescription = "Avatar seleccionado",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text("Avatar seleccionado", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Text("Avatares Disponibles", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            names.chunked(3).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    row.forEach { name ->
                        AvatarItem(name, name == selectedAvatar) {
                            selectedAvatar = name
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            Button(
                onClick = {
                    cambiarFotoPerfil(selectedAvatar)
                    navController.navigate(MyAppRoute.ACCOUNT)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Selección", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun cambiarFotoPerfil(selectedAvatar: String) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val db = FirebaseFirestore.getInstance()
    val userRef = currentUser?.let { db.collection("usuarios").document(it.uid) }

    if (userRef != null) {
        userRef.update("fotoPerfil", selectedAvatar)
    }
}

@Composable
fun AvatarItem(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() }
            .padding(10.dp)
            .background(if (isSelected) Color(0xFF800080) else Color(0x26FFFFFF), RoundedCornerShape(15.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = rememberAsyncImagePainter("https://api.dicebear.com/7.x/avataaars/png?seed=$name"),
            contentDescription = "Avatar de $name",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}
