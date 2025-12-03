package com.example.golden_rose_apk.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(navController: NavController) { // Añadimos NavController
    Scaffold(topBar = { TopAppBar(title = { Text("Blog") }) }) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            Text("Pantalla del Blog (Lista)")
            // Aquí iría tu LazyColumn leyendo blogs.json más adelante
        }
    }
}