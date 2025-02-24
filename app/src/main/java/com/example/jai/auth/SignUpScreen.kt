package com.example.jai.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jai.navBar.MyAppRoute
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val backgroundColor = Color(20,19,24)
    var db = FirebaseFirestore.getInstance()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sign Up",color =  Color.White, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email",color =  Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre",color =  Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",color =  Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password",color =  Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (validate(email, password, confirmPassword, nombre, auth, { errorMessage = it })) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                crearUsuarioBD(db,email, password, confirmPassword, nombre, auth);
                                navController.navigate(MyAppRoute.HOME)
                            } else {
                                errorMessage = task.exception?.message ?: "Ha ocurrido un error al intentar iniciar sesion"
                            }
                        }
                }
            }) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                navController.navigate(MyAppRoute.LOGIN)
            }) {
                Text("¿Ya tienes cuenta? Inicia sesion",color =  Color.White)
            }

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }

}

fun crearUsuarioBD(bd: FirebaseFirestore,email: String, password: String, confirmPassword: String, nombre: String, auth: FirebaseAuth){
    val user = FirebaseAuth.getInstance().currentUser;
    val nombres = listOf(
        "Carlos", "Ana", "Pedro", "Maria", "Luis",
        "Sofia", "Juan", "Elena", "Miguel", "Laura"
    )

    val nombreAleatorio = nombres.random()

    val userMap = hashMapOf(
    "nombre" to nombre,
    "email" to email,
    "fotoPerfil" to nombreAleatorio
    )

    if (user != null) {
        bd.collection("usuarios")
            .document(user.uid)
            .set(userMap)
    }
}

fun validate(email: String, password: String, confirmPassword: String, nombre: String, auth: FirebaseAuth, setErrorMessage: (String) -> Unit): Boolean {
    val regex = "^[A-Za-z0-9._%+-]+@(gmail\\.com|hotmail\\.com|yahoo\\.com)$"

    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || nombre.isEmpty()) {
        setErrorMessage("Todos los campos son obligatorios")
        return false
    }

    if(!(email.matches(regex.toRegex())))
    {
        setErrorMessage("Por favor, ingresa un email válido con un dominio permitido (gmail.com, hotmail.com, etc.)")
        return false
    }

    if (password != confirmPassword) {
        setErrorMessage("Las contraseñas no coinciden")
        return false
    }
    var isValid = true

    return isValid
}

