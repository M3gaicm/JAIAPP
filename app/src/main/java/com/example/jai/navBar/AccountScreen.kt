package com.example.jai.navBar

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun AccountScreen() {
    val backgroundColor = MaterialTheme.colorScheme.surface
    Log.d("HomeScreen", "Recomposed!")

    CenteredContent(backgroundColor = backgroundColor) {
        Text(
            text = "Account",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}



