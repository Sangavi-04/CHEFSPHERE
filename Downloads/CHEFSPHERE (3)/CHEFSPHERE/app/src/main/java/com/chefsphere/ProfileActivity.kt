package com.chefsphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeacockTheme {
                ChefSphereProfileScreen()
            }
        }
    }
}

@Composable
fun ChefSphereProfileScreen() {
    // Implement the 6-step profile creation flow here (see next file for full details)
    // Use the React code structure as a reference for Compose implementation
    // ...
}
