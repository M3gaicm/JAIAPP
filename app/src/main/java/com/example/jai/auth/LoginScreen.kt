package com.example.jai.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jai.navBar.MyAppRoute
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.jai.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val backgroundColor = colorResource(id = R.color.background)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor), // Aplica el color de fondo
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login", style = MaterialTheme.typography.headlineMedium,color =  Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email",color =  Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",color =  Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                validate(email, password, auth, { errorMessage = it }) { isValid ->
                    if (isValid) {
                        navController.navigate(MyAppRoute.HOME) {
                            popUpTo(MyAppRoute.LOGIN) { inclusive = true }
                        }
                    }
                }
            }) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                navController.navigate(MyAppRoute.SIGNUP)
            }) {
                Text("No tienes cuenta? Registrate", color = Color.White)
            }

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }

}

fun validate(email: String, password: String, auth: FirebaseAuth, setErrorMessage: (String) -> Unit, onValidationComplete: (Boolean) -> Unit) {
    if (email.isEmpty() || password.isEmpty()) {
        setErrorMessage("Todos los campos son obligatorios")
        onValidationComplete(false)
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onValidationComplete(true)
            } else {
                setErrorMessage("Contraseña o email incorrectos")
                onValidationComplete(false)
            }
        }
}





