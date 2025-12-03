package com.example.golden_rose_apk.Screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.Calendar






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

    // Validaciones de contraseña
    val hasMinLength = password.length >= 8
    val hasMaxLength = password.length <= 100
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasLowerCase = password.any { it.isLowerCase() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Crea tu Cuenta",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF5649A5),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFE7F1F1))
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Sección: Información Personal
            Text(
                text = "Información Personal",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF5649A5),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Campo: Usuario
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Usuario",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text("Ingresa tu nombre de usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5649A5),
                        focusedLabelColor = Color(0xFF5649A5)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Fecha de Nacimiento
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Fecha de Nacimiento",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                SelectorFechas()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Teléfono
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Teléfono",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Ingresa tu número de teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5649A5),
                        focusedLabelColor = Color(0xFF5649A5)
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Línea divisoria
            Divider(
                color = Color(0xFF5649A5).copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sección: Información Cuenta
            Text(
                text = "Información Cuenta",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF5649A5),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Campo: Correo
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Correo Electrónico",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("ejemplo@correo.com") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5649A5),
                        focusedLabelColor = Color(0xFF5649A5)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Contraseña con validaciones
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Contraseña",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Crea una contraseña segura") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5649A5),
                        focusedLabelColor = Color(0xFF5649A5)
                    )
                )

                // REQUISITOS DE CONTRASEÑA
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF5649A5).copy(alpha = 0.05f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Requisitos de contraseña:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5649A5)
                    )

                    // Cada requisito con icono de validación
                    RequerimientoContraseñaItem(
                        text = "Entre 8 y 100 caracteres",
                        isValid = hasMinLength && hasMaxLength
                    )

                    RequerimientoContraseñaItem(
                        text = "Al menos una mayúscula y una minúscula",
                        isValid = hasUpperCase && hasLowerCase
                    )

                    RequerimientoContraseñaItem(
                        text = "Al menos un carácter especial (@, #, $, etc.)",
                        isValid = hasSpecialChar
                    )

                    // Indicador de fortaleza
                    if (password.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        IndicadorContraseñaFuerte(
                            hasMinLength = hasMinLength,
                            hasMaxLength = hasMaxLength,
                            hasUpperCase = hasUpperCase,
                            hasLowerCase = hasLowerCase,
                            hasSpecialChar = hasSpecialChar
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Checkbox Términos y Condiciones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = accepted,
                    onCheckedChange = { accepted = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF5649A5),
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "He leído y acepto los términos y condiciones",
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { accepted = !accepted }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Crear Cuenta
            Button(
                onClick = {
                    if (accepted &&
                        email.isNotBlank() &&
                        password.isNotBlank() &&
                        hasMinLength && hasMaxLength &&
                        hasUpperCase && hasLowerCase &&
                        hasSpecialChar &&
                        user.isNotBlank() &&
                        phone.isNotBlank()) {

                        // Aquí iría la lógica de registro real
                        navController.navigate("home") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5649A5),
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = accepted &&
                        email.isNotBlank() &&
                        password.isNotBlank() &&
                        hasMinLength && hasMaxLength &&
                        hasUpperCase && hasLowerCase &&
                        hasSpecialChar &&
                        user.isNotBlank() &&
                        phone.isNotBlank()
            ) {
                Text(
                    "Crear Cuenta",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Enlace para iniciar sesión
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                fontSize = 14.sp,
                color = Color(0xFF5649A5),
                modifier = Modifier
                    .clickable { navController.navigate("login") }
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun SelectorFechas() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var dateText by remember { mutableStateOf("") }

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateText = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = dateText,
            onValueChange = {},
            label = { Text("Selecciona tu fecha de nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF5649A5),
                focusedLabelColor = Color(0xFF5649A5)
            )
        )

        Button(
            onClick = { datePicker.show() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5649A5),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Seleccionar fecha")
        }
    }
}

@Composable
fun RequerimientoContraseñaItem(text: String, isValid: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = if (isValid) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
            contentDescription = null,
            tint = if (isValid) Color(0xFF4CAF50) else Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isValid) Color(0xFF4CAF50) else Color.Gray
        )
    }
}

// Indicador visual de fortaleza de contraseña
@Composable
fun IndicadorContraseñaFuerte(
    hasMinLength: Boolean,
    hasMaxLength: Boolean,
    hasUpperCase: Boolean,
    hasLowerCase: Boolean,
    hasSpecialChar: Boolean
) {
    val requirementsMet = listOf(
        hasMinLength && hasMaxLength,
        hasUpperCase && hasLowerCase,
        hasSpecialChar
    ).count { it }

    val strengthText = when (requirementsMet) {
        3 -> "Fuerte ✓"
        2 -> "Media"
        1 -> "Débil"
        else -> "Muy débil"
    }

    val strengthColor = when (requirementsMet) {
        3 -> Color(0xFF4CAF50)  // Verde
        2 -> Color(0xFFFF9800)  // Naranja
        1 -> Color(0xFFF44336)  // Rojo
        else -> Color.Gray
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Barra de progreso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            color = if (index < requirementsMet) strengthColor else Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }

        // Texto de fortaleza
        Text(
            text = "Fortaleza: $strengthText",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = strengthColor
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}