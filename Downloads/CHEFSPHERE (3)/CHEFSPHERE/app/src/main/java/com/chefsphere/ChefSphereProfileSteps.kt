package com.chefsphere

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChefSphereProfileScreen() {
    var currentStep by remember { mutableStateOf(1) }
    val steps = listOf(
        StepData("Basic Info", Icons.Default.Person),
        StepData("Health Goals", Icons.Default.Flag),
        StepData("Health Status", Icons.Default.Favorite),
        StepData("Food Preferences", Icons.Default.Restaurant),
        StepData("Restrictions", Icons.Default.Warning),
        StepData("Cooking Style", Icons.Default.Settings)
    )
    // Profile state (all fields)
    val profile = remember { mutableStateOf(ProfileData()) }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFe6fffa)).padding(16.dp)) {
        // Progress Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { idx, step ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            if (currentStep > idx) Color(0xFF1e7074) else Color(0xFF4fd1c7),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(step.icon, contentDescription = null, tint = Color.White)
                }
                if (idx < steps.lastIndex) {
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .width(24.dp)
                            .background(if (currentStep > idx + 1) Color(0xFF1e7074) else Color(0xFF4fd1c7))
                    )
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
        // Step Content
        when (currentStep) {
            1 -> BasicInfoStep(profile.value) { field, value -> profile.value = profile.value.copyField(field, value) }
            2 -> HealthGoalsStep(profile.value) { field, value -> profile.value = profile.value.copyField(field, value) }
            3 -> HealthStatusStep(profile.value) { field, value -> profile.value = profile.value.copyField(field, value) }
            4 -> FoodPreferencesStep(profile.value) { field, value -> profile.value = profile.value.copyField(field, value) }
            5 -> RestrictionsStep(profile.value) { field, value -> profile.value = profile.value.copyField(field, value) }
            6 -> CookingPreferencesStep(profile.value) { field, value -> profile.value = profile.value.copyField(field, value) }
        }
        Spacer(Modifier.height(24.dp))
        // Navigation
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { if (currentStep > 1) currentStep-- },
                enabled = currentStep > 1,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4fd1c7))
            ) { Text("Previous", color = Color.White) }
            Button(
                onClick = {
                    if (currentStep < 6) currentStep++
                    else {/* Save profile to DB and finish */}
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1e7074))
            ) { Text(if (currentStep == 6) "Complete Profile" else "Next", color = Color.White) }
        }
    }
}

data class StepData(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
data class ProfileData(
    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val height: String = "",
    val weight: String = "",
    val activityLevel: String = "",
    val primaryGoal: String = "",
    val targetWeight: String = "",
    val timeframe: String = "",
    val healthConditions: List<String> = emptyList(),
    val medications: String = "",
    val dietType: String = "",
    val cuisinePrefs: List<String> = emptyList(),
    val allergies: List<String> = emptyList(),
    val restrictions: List<String> = emptyList(),
    val showAllergies: Boolean = false,
    val customAllergies: String = "",
    val skillLevel: String = "",
    val cookingTime: String = "",
    val portionSize: String = ""
) {
    fun copyField(field: String, value: Any): ProfileData {
        return when (field) {
            "name" -> copy(name = value as String)
            "age" -> copy(age = value as String)
            "gender" -> copy(gender = value as String)
            "height" -> copy(height = value as String)
            "weight" -> copy(weight = value as String)
            "activityLevel" -> copy(activityLevel = value as String)
            "primaryGoal" -> copy(primaryGoal = value as String)
            "targetWeight" -> copy(targetWeight = value as String)
            "timeframe" -> copy(timeframe = value as String)
            "healthConditions" -> copy(healthConditions = value as List<String>)
            "medications" -> copy(medications = value as String)
            "dietType" -> copy(dietType = value as String)
            "cuisinePrefs" -> copy(cuisinePrefs = value as List<String>)
            "allergies" -> copy(allergies = value as List<String>)
            "restrictions" -> copy(restrictions = value as List<String>)
            "showAllergies" -> copy(showAllergies = value as Boolean)
            "customAllergies" -> copy(customAllergies = value as String)
            "skillLevel" -> copy(skillLevel = value as String)
            "cookingTime" -> copy(cookingTime = value as String)
            "portionSize" -> copy(portionSize = value as String)
            else -> this
        }
    }
}

// Implement each step as a composable (BasicInfoStep, HealthGoalsStep, etc.)
// For brevity, only the structure is shown here. You can expand each step as per your requirements.
@Composable
fun BasicInfoStep(profile: ProfileData, onChange: (String, Any) -> Unit) {
    // ...fields for name, age, gender, height, weight, activity level...
}
@Composable
fun HealthGoalsStep(profile: ProfileData, onChange: (String, Any) -> Unit) {}
@Composable
fun HealthStatusStep(profile: ProfileData, onChange: (String, Any) -> Unit) {}
@Composable
fun FoodPreferencesStep(profile: ProfileData, onChange: (String, Any) -> Unit) {}
@Composable
fun RestrictionsStep(profile: ProfileData, onChange: (String, Any) -> Unit) {}
@Composable
fun CookingPreferencesStep(profile: ProfileData, onChange: (String, Any) -> Unit) {}
