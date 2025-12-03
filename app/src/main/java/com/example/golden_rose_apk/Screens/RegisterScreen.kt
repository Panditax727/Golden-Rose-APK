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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun RegisterScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7F1F1))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Crea tu cuenta", fontSize = 20.sp)

        Spacer(Modifier.height(30.dp))
        Text("Información Personal")

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = "", onValueChange = {},
                label = { Text("DD") },
                modifier = Modifier.width(80.dp)
            )
            OutlinedTextField(
                value = "", onValueChange = {},
                label = { Text("MM") },
                modifier = Modifier.width(80.dp)
            )
            OutlinedTextField(
                value = "", onValueChange = {},
                label = { Text("YYYY") },
                modifier = Modifier.width(100.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(30.dp))
        Text("Información Cuenta")

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(false, {})
            Text("He leído los términos y condiciones")
        }

        Spacer(Modifier.height(25.dp))

        Button(
            onClick = { /* register */ },
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
