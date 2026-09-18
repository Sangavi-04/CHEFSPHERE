package com.chefsphere

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeacockTheme {
                val context = this
                val historyList = remember { mutableStateListOf<HistoryItem>() }
                // Load history from DB
                LaunchedEffect(Unit) {
                    val db = ChefSphereDbHelper(context)
                    val prefs = context.getSharedPreferences("chefsphere", Context.MODE_PRIVATE)
                    val email = prefs.getString("user_email", "") ?: ""
                    val userCursor = db.readableDatabase.rawQuery("SELECT id FROM users WHERE email=?", arrayOf(email))
                    if (userCursor.moveToFirst()) {
                        val userId = userCursor.getInt(0)
                        val cursor = db.readableDatabase.rawQuery("SELECT recipe_name, cooked_at, cooking_level FROM history WHERE user_id=?", arrayOf(userId.toString()))
                        while (cursor.moveToNext()) {
                            historyList.add(
                                HistoryItem(
                                    recipeName = cursor.getString(0),
                                    cookedAt = cursor.getString(1),
                                    cookingLevel = cursor.getString(2)
                                )
                            )
                        }
                        cursor.close()
                    }
                    userCursor.close()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color(0xFFe6fffa), Color(0xFFf0fdfa)))),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                        Text("Cooking History", fontSize = 24.sp, color = Color(0xFF1e7074), style = MaterialTheme.typography.h5)
                        Spacer(Modifier.height(16.dp))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(historyList.size) { idx ->
                                val item = historyList[idx]
                                Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(item.recipeName, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 18.sp)
                                        Text("Cooked at: ${item.cookedAt}", color = Color.Gray, fontSize = 14.sp)
                                        Text("Level: ${item.cookingLevel}", color = Color(0xFF059669), fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
data class HistoryItem(val recipeName: String, val cookedAt: String, val cookingLevel: String)
