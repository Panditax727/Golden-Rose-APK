package com.example.golden_rose_apk.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.golden_rose_apk.ViewModel.BlogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(navController: NavController, viewModel: BlogViewModel = viewModel()) {
    val blogs by viewModel.blogs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center

                    ) {
                        Text("Blog")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            if (blogs.isEmpty()) {
                Text("No hay blogs aún", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()
                    .padding(20.dp)) {
                    items(blogs) { blog ->
                        BlogItem(post = blog) {
                            // Aquí puedes manejar el click, por ejemplo:
                            // navController.navigate("blogDetail/${blog.id}")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BlogScreenPreview() {
    BlogScreen(navController = rememberNavController())
}
