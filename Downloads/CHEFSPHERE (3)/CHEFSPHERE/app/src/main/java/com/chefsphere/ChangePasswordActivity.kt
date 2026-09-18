package com.chefsphere

import android.content.Context
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

class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeacockTheme {
                var currentPassword by remember { mutableStateOf("") }
                var newPassword by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }
                var message by remember { mutableStateOf("") }
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
                        Text("Change Password", fontSize = 24.sp, color = Color(0xFF1e7074), style = MaterialTheme.typography.h5)
                        Spacer(Modifier.height(24.dp))
                        OutlinedTextField(
                            value = currentPassword,
                            onValueChange = { currentPassword = it },
                            label = { Text("Current Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("New Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm New Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = {
                                val prefs = context.getSharedPreferences("chefsphere", Context.MODE_PRIVATE)
                                val email = prefs.getString("user_email", "") ?: ""
                                val db = ChefSphereDbHelper(context)
                                val cursor = db.readableDatabase.rawQuery("SELECT * FROM users WHERE email=? AND password=?", arrayOf(email, currentPassword))
                                if (!cursor.moveToFirst()) {
                                    message = "Current password is incorrect"
                                } else if (newPassword != confirmPassword) {
                                    message = "New passwords do not match"
                                } else {
                                    db.writableDatabase.execSQL("UPDATE users SET password=? WHERE email=?", arrayOf(newPassword, email))
                                    message = "Password changed successfully"
                                }
                                cursor.close()
                            },
                            modifier = Modifier.fillMaxWidth(0.85f),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1e7074))
                        ) {
                            Text("Change Password", color = Color.White)
                        }
                        Spacer(Modifier.height(8.dp))
                        if (message.isNotEmpty()) {
                            Text(message, color = if (message.contains("success")) Color(0xFF059669) else Color.Red, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
