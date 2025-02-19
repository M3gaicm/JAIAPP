package com.example.jai.navBar

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun SettingsScreen() {
    val backgroundColor = MaterialTheme.colorScheme.surface // Directly access the background color
    Log.d("HomeScreen", "Recomposed!") // Debugging in Logcat

    CenteredContent(backgroundColor = backgroundColor) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}


