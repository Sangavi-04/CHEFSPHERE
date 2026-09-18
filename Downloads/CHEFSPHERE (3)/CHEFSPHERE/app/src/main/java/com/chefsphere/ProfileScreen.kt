package com.chefsphere

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ChefSphereProfilePage(onProfileComplete: () -> Unit) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(1) }
    val steps = listOf(
        StepData("Basic Info", Icons.Default.Person),
        StepData("Health Goals", Icons.Default.Flag),
        StepData("Health Status", Icons.Default.Favorite),
        StepData("Food Preferences", Icons.Default.Restaurant),
        StepData("Restrictions", Icons.Default.Warning),
        StepData("Cooking Style", Icons.Default.Settings)
    )
    var profile by remember { mutableStateOf(ProfileData()) }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFe6fffa), Color(0xFFf0fdfa))))
            .padding(8.dp)
    ) {
        // Progress Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { idx, step ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            when {
                                currentStep > idx + 1 -> Color(0xFF059669)
                                currentStep == idx + 1 -> Color(0xFF1e7074)
                                else -> Color(0xFF4fd1c7)
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentStep > idx + 1) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                    } else {
                        Icon(step.icon, contentDescription = null, tint = Color.White)
                    }
                }
                if (idx < steps.lastIndex) {
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .width(24.dp)
                            .background(if (currentStep > idx + 1) Color(0xFF059669) else Color(0xFF4fd1c7))
                    )
                }
            }
        }
        // Step Content
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 6.dp,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Crossfade(targetState = currentStep) { step ->
                    when (step) {
                        1 -> BasicInfoStep(profile) { f, v -> profile = profile.copyField(f, v) }
                        2 -> HealthGoalsStep(profile) { f, v -> profile = profile.copyField(f, v) }
                        3 -> HealthStatusStep(profile) { f, v -> profile = profile.copyField(f, v) }
                        4 -> FoodPreferencesStep(profile) { f, v -> profile = profile.copyField(f, v) }
                        5 -> RestrictionsStep(profile) { f, v -> profile = profile.copyField(f, v) }
                        6 -> CookingPreferencesStep(profile) { f, v -> profile = profile.copyField(f, v) }
                    }
                }
                if (error.isNotEmpty()) {
                    Text(error, color = Color.Red, fontSize = 14.sp, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp))
                }
            }
        }
        // Navigation
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (currentStep > 1) currentStep-- },
                enabled = currentStep > 1,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4fd1c7))
            ) { Text("Previous", color = Color.White) }
            Button(
                onClick = {
                    if (currentStep < 6) {
                        currentStep++
                        error = ""
                    } else {
                        // Validate and save to DB
                        val missing = profile.validate()
                        if (missing.isNotEmpty()) {
                            error = "Please fill: ${missing.joinToString()}"
                        } else {
                            saveProfileToDb(context, profile)
                            Toast.makeText(context, "Profile saved!", Toast.LENGTH_SHORT).show()
                            onProfileComplete()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1e7074))
            ) { Text(if (currentStep == 6) "Complete Profile" else "Next", color = Color.White) }
        }
        // Disclaimer
        Card(
            backgroundColor = Color(0xFFe6fffa),
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Text(
                "Health Disclaimer: ChefSphere provides nutritional guidance and recipe recommendations based on your profile. This is not medical advice. Please consult healthcare professionals for specific dietary needs or health conditions.",
                color = Color(0xFF0f5257), fontSize = 13.sp, modifier = Modifier.padding(8.dp)
            )
        }
    }
}

// Data and helpers
// ... (see previous message for full code, including ProfileData, saveProfileToDb, and all step composables)
