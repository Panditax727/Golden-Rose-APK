package com.example.golden_rose_apk.Screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.golden_rose_apk.ViewModel.AuthViewModel
import com.example.golden_rose_apk.ViewModel.AuthViewModelFactory
import com.example.golden_rose_apk.ViewModel.SettingsViewModel
import com.example.golden_rose_apk.ViewModel.SettingsViewModelFactory
import com.example.golden_rose_apk.model.BottomNavItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(application))
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(application))

    val username by settingsViewModel.username.collectAsState()
    val receiveOffers by settingsViewModel.receiveOffers.collectAsState()
    val currentTheme by settingsViewModel.appTheme.collectAsState()
    val pushNotificationsEnabled by settingsViewModel.pushNotificationsEnabled.collectAsState()

    val navItems = listOf(
        BottomNavItem("Inicio", Icons.Filled.Home, "home"),
        BottomNavItem("Categorías", Icons.Filled.Category, "categories"),
        BottomNavItem("Blogs", Icons.Filled.Article, "blogs"),
        BottomNavItem("Perfil", Icons.Filled.Person, "perfil")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi Perfil",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    // Espacio invisible para balancear el navigationIcon
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5649A5),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            HomeBottomNavigationBar(
                navController = navController,
                navItems = navItems
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Avatar y nombre de usuario
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                // Avatar circular
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFF5649A5), androidx.compose.foundation.shape.CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = username.take(1).uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(username, style = MaterialTheme.typography.headlineSmall)
            }

            // Botón Editar Perfil
            TextButton(
                onClick = { navController.navigate("edit_profile") },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Editar Perfil")
            }

            SettingItemDivider(title = "Preferencias")

            SettingSwitchItem(
                text = "Recibir ofertas por correo",
                icon = Icons.Default.Email,
                checked = receiveOffers,
                onCheckedChange = { settingsViewModel.setReceiveOffers(it) }
            )
            SettingSwitchItem(
                text = "Activar notificaciones push",
                icon = Icons.Default.Notifications,
                checked = pushNotificationsEnabled,
                onCheckedChange = { settingsViewModel.setPushNotificationsEnabled(it) }
            )

            SettingItemDivider(title = "Apariencia")
            Column(Modifier.selectableGroup().padding(horizontal = 16.dp)) {
                ThemeOptionRow(
                    text = "Claro",
                    selected = currentTheme == "light",
                    onClick = { settingsViewModel.setAppTheme("light") }
                )
                ThemeOptionRow(
                    text = "Oscuro",
                    selected = currentTheme == "dark",
                    onClick = { settingsViewModel.setAppTheme("dark") }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Cerrar Sesión
            Button(
                onClick = {
                    Log.d("SettingsScreen", "Logout button clicked!")
                    authViewModel.logout()
                    // Navegar al inicio después de cerrar sesión
                    navController.navigate("welcome") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Cerrar Sesión")
            }
        }
    }
}

@Composable
fun SettingItemDivider(title: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )
        Divider()
    }
}

@Composable
fun SettingSwitchItem(
    text: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}



@Composable
fun ThemeOptionRow(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(navController = rememberNavController())
}