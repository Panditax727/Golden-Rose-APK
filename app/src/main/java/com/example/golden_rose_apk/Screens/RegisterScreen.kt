package com.example.golden_rose_apk.Screens

import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {

    var user by remember { mutableStateOf("") }
    var dd by remember { mutableStateOf("") }
    var mm by remember { mutableStateOf("") }
    var yyyy by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var accepted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crea tu Cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        modifier = Modifier.background(Color(0xFFE7F1F1))
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFE7F1F1))
                .padding(30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Crea tu cuenta", fontSize = 20.sp)

            Spacer(Modifier.height(30.dp))
            Text("Información Personal")

            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = dd,
                    onValueChange = { dd = it },
                    label = { Text("DD") },
                    modifier = Modifier.width(80.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = mm,
                    onValueChange = { mm = it },
                    label = { Text("MM") },
                    modifier = Modifier.width(80.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = yyyy,
                    onValueChange = { yyyy = it },
                    label = { Text("YYYY") },
                    modifier = Modifier.width(100.dp),
                    singleLine = true
                )
            }

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(30.dp))
            Text("Información Cuenta")

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = accepted,
                    onCheckedChange = { accepted = it }
                )
                Text("He leído los términos y condiciones")
            }

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {
                    if (accepted && email.isNotBlank() && password.isNotBlank()) {
                        navController.navigate("home")
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF5649A5)),
                shape = RoundedCornerShape(50)
            ) {
                Text("Crear Cuenta", color = Color.White)
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}