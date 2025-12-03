package com.example.golden_rose_apk.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {
    var receiveOffers by remember { mutableStateOf(true) }
    var pushNotifications by remember { mutableStateOf(true) }
    var appearanceMode by remember { mutableStateOf("claro") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Perfil",
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
        },
        bottomBar = {
            BottomNavigationBar(
                navItems = listOf(
                    BottomNavItem("Inicio", Icons.Filled.Home),
                    BottomNavItem("Categorías", Icons.Filled.Category),
                    BottomNavItem("Blogs", Icons.Filled.Article),
                    BottomNavItem("Perfil", Icons.Filled.Person)
                ),
                selectedItem = "Perfil",
                onItemSelected = { item ->
                    when (item) {
                        "Inicio" -> navController.navigate("home")
                        "Categorías" -> navController.navigate("categories")
                        "Blogs" -> navController.navigate("blogs")
                        "Perfil" -> { /* Ya estamos aquí */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
        ) {
            // Sección 1: Información de usuario
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar del usuario
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF5649A5).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "GR",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5649A5)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Usuario Golden",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5649A5)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "usuario@example.com",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón Editar Perfil - CORREGIDO
                    Button(
                        onClick = { navController.navigate("edit_profile") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFF5649A5)
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 2.dp,
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF5649A5))
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp
                        )
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Editar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Editar Perfil",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Línea divisoria
            item {
                Divider(
                    color = Color.Gray.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Sección 2: Preferencias
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Preferencias",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5649A5),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Recibir ofertas por correo
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = "Correo",
                                tint = Color(0xFF5649A5),
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = "Recibir Ofertas por correo",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Promociones y descuentos especiales",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Switch(
                            checked = receiveOffers,
                            onCheckedChange = { receiveOffers = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF5649A5),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                    }

                    // Notificaciones push
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Filled.Notifications,
                                contentDescription = "Notificaciones",
                                tint = Color(0xFF5649A5),
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = "Activar Notificaciones Push",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Alertas y recordatorios",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Switch(
                            checked = pushNotifications,
                            onCheckedChange = { pushNotifications = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF5649A5),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                    }

                    // Apariencia
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(
                                Icons.Filled.Palette,
                                contentDescription = "Apariencia",
                                tint = Color(0xFF5649A5),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Apariencia",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }

                        // Opciones de apariencia - CORREGIDO
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Tema claro
                            Box(
                                modifier = Modifier
                                    .weight(1f)  // ✅ CORREGIDO: No es una función
                            ) {
                                AppearanceOption(
                                    text = "Claro",
                                    isSelected = appearanceMode == "claro",
                                    icon = Icons.Filled.LightMode,
                                    onClick = { appearanceMode = "claro" }
                                )
                            }

                            // Tema oscuro
                            Box(
                                modifier = Modifier
                                    .weight(1f)  // ✅ CORREGIDO: No es una función
                            ) {
                                AppearanceOption(
                                    text = "Oscuro",
                                    isSelected = appearanceMode == "oscuro",
                                    icon = Icons.Outlined.DarkMode,
                                    onClick = { appearanceMode = "oscuro" }
                                )
                            }
                        }
                    }
                }
            }

            // Línea divisoria
            item {
                Divider(
                    color = Color.Gray.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Sección 3: Cerrar sesión y más opciones
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    // Cerrar sesión - CORREGIDO
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF44336).copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Filled.Logout,
                                contentDescription = "Cerrar sesión",
                                tint = Color(0xFFF44336),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Cerrar Sesión",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF44336)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Información adicional
                    Text(
                        text = "Más opciones",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5649A5),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Opciones adicionales
                    ProfileOptionItem(
                        icon = Icons.Filled.Help,
                        title = "Ayuda y Soporte",
                        subtitle = "Preguntas frecuentes y contacto",
                        onClick = { navController.navigate("help") }
                    )

                    ProfileOptionItem(
                        icon = Icons.Filled.Security,
                        title = "Privacidad y Seguridad",
                        subtitle = "Configuración de privacidad",
                        onClick = { navController.navigate("privacy") }
                    )

                    ProfileOptionItem(
                        icon = Icons.Filled.Info,
                        title = "Acerca de",
                        subtitle = "Versión 1.0.0",
                        onClick = { navController.navigate("about") }
                    )
                }
            }

            // Espacio final
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// Componente para opciones de apariencia - CORREGIDO
@Composable
fun AppearanceOption(
    text: String,
    isSelected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF5649A5).copy(alpha = 0.1f) else Color.White
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder(
                color = Color(0xFF5649A5),
                width = 2.dp
            )
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isSelected) Color(0xFF5649A5) else Color.Gray,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF5649A5) else Color.Gray
            )
        }
    }
}

// Componente para opciones del perfil - CORREGIDO
@Composable
fun ProfileOptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF5649A5),
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = "Ver más",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Barra de navegación inferior - CORREGIDO
@Composable
fun BottomNavigationBar(
    navItems: List<BottomNavItem>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.shadow(elevation = 8.dp)
    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item.title,
                onClick = { onItemSelected(item.title) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selectedItem == item.title) Color(0xFF5649A5) else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedItem == item.title) Color(0xFF5649A5) else Color.Gray
                    )
                }
            )
        }
    }
}

// Data class para items de navegación
data class BottomNavItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(navController = rememberNavController())
}