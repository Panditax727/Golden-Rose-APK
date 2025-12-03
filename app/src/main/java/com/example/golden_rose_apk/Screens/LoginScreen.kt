package com.example.golden_rose_apk.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.golden_rose_apk.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by authViewModel.state.collectAsState()

    LaunchedEffect(state.token) {
        if (state.token != null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Iniciar sesión") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        modifier = Modifier.background(Color(0xFFE7F1F1))
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Inicio Sesión", fontSize = 22.sp)

            Spacer(Modifier.height(50.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { authViewModel.login(email, password) },
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF5649A5)),
                shape = RoundedCornerShape(50)
            ) {
                Text(if (state.loading) "Cargando..." else "Iniciar Sesión", color = Color.White)
            }

            state.error?.let {
                Spacer(Modifier.height(12.dp))
                Text("Error: $it", color = Color.Red)
            }
        }
    }
}
