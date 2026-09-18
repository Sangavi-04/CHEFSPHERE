package com.chefsphere

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeacockTheme {
                var selectedTab by remember { mutableStateOf(0) }
                val navItems = listOf("Home", "Favourite", "Profile")
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = Color(0xFF1e7074),
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painterResource(R.drawable.peacock_logo), contentDescription = null, modifier = Modifier.size(32.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("CHEF SPHERE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* open nav drawer */ }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(backgroundColor = Color.White) {
                            navItems.forEachIndexed { index, item ->
                                BottomNavigationItem(
                                    icon = {
                                        when (item) {
                                            "Home" -> Icon(Icons.Default.Home, contentDescription = null)
                                            "Favourite" -> Icon(Icons.Default.Favorite, contentDescription = null)
                                            "Profile" -> Icon(Icons.Default.Person, contentDescription = null)
                                        }
                                    },
                                    label = { Text(item) },
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    selectedContentColor = Color(0xFF1e7074),
                                    unselectedContentColor = Color.Gray
                                )
                            }
                        }
                    }
                ) { padding ->
                    Column(modifier = Modifier.padding(padding).fillMaxSize().background(Color.White)) {
                        // Search bar
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            leadingIcon = {
                                Icon(painterResource(R.drawable.ic_pan_search), contentDescription = null, tint = Color(0xFF1e7074))
                            },
                            placeholder = { Text("Search efficiently") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(CircleShape),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF1e7074),
                                unfocusedBorderColor = Color(0xFF4fd1c7)
                            )
                        )
                        // 3 main buttons
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { /* AI Recipe Generator */ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4fd1c7)),
                                modifier = Modifier.weight(1f).padding(end = 4.dp)
                            ) { Text("AI Recipe Generator", color = Color.White) }
                            Button(
                                onClick = { /* Smart Kitchen */ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF059669)),
                                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                            ) { Text("Smart Kitchen", color = Color.White) }
                            Button(
                                onClick = { /* Nutrition */ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFdc2626)),
                                modifier = Modifier.weight(1f).padding(start = 4.dp)
                            ) { Text("Nutrition", color = Color.White) }
                        }
                        Spacer(Modifier.height(16.dp))
                        // Recipe cards (scrollable)
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                            items(10) { idx ->
                                Card(
                                    elevation = 4.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(60.dp)
                                                .background(Color(0xFFe6fffa), shape = CircleShape)
                                        )
                                        Spacer(Modifier.width(16.dp))
                                        Column {
                                            Text("Recipe Name $idx", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                            Text("Short description for recipe $idx", color = Color.Gray, fontSize = 14.sp)
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
}
