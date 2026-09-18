
package com.chefsphere

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

fun getPasswordStrength(password: String): Pair<String, Color> {
    return when {
        password.length >= 10 && password.any { it.isDigit() } && password.any { it.isUpperCase() } && password.any { !it.isLetterOrDigit() } -> "Strong" to Color(0xFF059669)
        password.length >= 6 -> "Medium" to Color(0xFFFFA500)
        else -> "Weak" to Color.Red
    }
}

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeacockTheme {
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }
                var error by remember { mutableStateOf("") }
                val context = this
                val (strength, strengthColor) = getPasswordStrength(password)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color(0xFFe6fffa), Color(0xFFf0fdfa)))),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text("CHEF SPHERE", fontSize = 28.sp, color = Color(0xFF1e7074), fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("Create Account", fontSize = 22.sp, color = Color(0xFF1e7074), fontWeight = FontWeight.Medium)
                        Spacer(Modifier.height(24.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Row(modifier = Modifier.fillMaxWidth(0.85f), verticalAlignment = Alignment.CenterVertically) {
                            Text("Strength: ", fontSize = 14.sp)
                            Text(strength, color = strengthColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(8.dp))
                        if (error.isNotEmpty()) {
                            Text(error, color = Color.Red, fontSize = 14.sp)
                        }
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                                    error = "All fields are required"
                                } else if (password != confirmPassword) {
                                    error = "Passwords do not match"
                                } else if (strength == "Weak") {
                                    error = "Password is too weak"
                                } else {
                                    val db = ChefSphereDbHelper(context)
                                    try {
                                        db.writableDatabase.execSQL("INSERT INTO users (email, password) VALUES (?, ?)", arrayOf(email, password))
                                        // Save login state for session persistence
                                        context.getSharedPreferences("chefsphere", Context.MODE_PRIVATE).edit().putString("user_email", email).apply()
                                        // Go to login page after signup
                                        context.startActivity(Intent(context, LoginActivity::class.java))
                                        (context as ComponentActivity).finish()
                                    } catch (e: Exception) {
                                        error = "Email already exists"
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.85f),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1e7074))
                        ) {
                            Text("Sign Up", color = Color.White)
                        }
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = {
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        }) {
                            Text("Already have an account? Login", color = Color(0xFF1e7074))
                        }
                    }
                }
            }
        }
    }
}
