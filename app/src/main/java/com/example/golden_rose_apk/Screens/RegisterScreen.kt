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

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by authViewModel.state.collectAsState()

    LaunchedEffect(state.token) {
        if (state.token != null) {
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7F1F1))
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(30.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Crea tu cuenta", fontSize = 20.sp)

            Spacer(Modifier.height(30.dp))

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = { authViewModel.register(nombre, correo, password) },
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF5649A5)),
                shape = RoundedCornerShape(50)
            ) {
                Text(if (state.loading) "Cargando..." else "Crear Cuenta", color = Color.White)
            }

            state.error?.let {
                Spacer(Modifier.height(12.dp))
                Text("Error: $it", color = Color.Red)
            }
        }
    }
}
