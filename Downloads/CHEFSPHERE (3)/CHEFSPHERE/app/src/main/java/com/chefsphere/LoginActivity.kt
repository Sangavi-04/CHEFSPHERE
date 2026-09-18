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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeacockTheme {
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var error by remember { mutableStateOf("") }
                val context = this
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
                        Text("CHEF SPHERE", fontSize = 28.sp, color = Color(0xFF1e7074), style = MaterialTheme.typography.h5)
                        Spacer(Modifier.height(32.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
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
                                val db = ChefSphereDbHelper(context)
                                val cursor = db.readableDatabase.rawQuery("SELECT * FROM users WHERE email=? AND password=?", arrayOf(email, password))
                                if (cursor.moveToFirst()) {
                                    // Save login state
                                    context.getSharedPreferences("chefsphere", Context.MODE_PRIVATE).edit().putString("user_email", email).apply()
                                    context.startActivity(Intent(context, HomeActivity::class.java))
                                    (context as ComponentActivity).finish()
                                } else {
                                    error = "Invalid credentials"
                                }
                                cursor.close()
                            },
                            modifier = Modifier.fillMaxWidth(0.85f),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1e7074))
                        ) {
                            Text("Login", color = Color.White)
                        }
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = {
                            // Forgot password logic (show dialog or navigate)
                        }) {
                            Text("Forgot Password?", color = Color(0xFF1e7074))
                        }
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = {
                            context.startActivity(Intent(context, SignupActivity::class.java))
                        }) {
                            Text("Don't have an account? Sign Up", color = Color(0xFF1e7074))
                        }
                    }
                }
            }
        }
    }
}
