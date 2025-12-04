package com.example.golden_rose_apk.Screens

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.golden_rose_apk.ViewModel.AuthViewModel
import com.example.golden_rose_apk.ViewModel.AuthViewModelFactory
import com.example.golden_rose_apk.ViewModel.SettingsViewModel
import com.example.golden_rose_apk.ViewModel.SettingsViewModelFactory
import com.example.golden_rose_apk.model.BottomNavItem
import java.io.File
import java.io.FileOutputStream


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

    // Estado para almacenar imagen
    var profileImageUri by remember { mutableStateOf<Uri?>(null)}

    // Launcher para abrir galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    // Launcher para tomar foto
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            // Convierte el bitmap en URI (se puede guardar en cache)
            val uri = saveBitmapToCache(context, bitmap)
            profileImageUri = uri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        }
    }


    // Configuración del Bottom Navigation
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
                Box(
                    modifier = Modifier.size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(profileImageUri),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color(0xFF5649A5), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = username.take(1).uppercase(),
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                }
            }

            // Boton para abrir camara
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = {
                    val permission =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
                            android.Manifest.permission.READ_MEDIA_IMAGES
                        else
                            android.Manifest.permission.READ_EXTERNAL_STORAGE

                    permissionLauncher.launch(permission)
                }) {
                    Icon(Icons.Default.Image, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Subir desde galería")
                }


                Spacer(Modifier.width(16.dp))

                TextButton(onClick = { cameraLauncher.launch(null) }) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Tomar foto")
                }
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

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
    val out = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    out.flush()
    out.close()
    return file.toUri()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(navController = rememberNavController())
}