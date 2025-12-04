package com.example.golden_rose_apk

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.golden_rose_apk.Screens.blogs.BlogScreen
import com.example.golden_rose_apk.Screens.categories.CategoriesScreen
import com.example.golden_rose_apk.Screens.HomeScreen
import com.example.golden_rose_apk.Screens.LoginScreen
import com.example.golden_rose_apk.Screens.perfil.PerfilScreen
import com.example.golden_rose_apk.Screens.RegisterScreen
import com.example.golden_rose_apk.Screens.WelcomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController, startDestination = "welcome") {
                composable("welcome") { WelcomeScreen(navController) }
                composable("login") { LoginScreen(navController) }
                composable("register") { RegisterScreen(navController) }

                // Pantalla principal con parámetro opcional
                composable("home") {
                    HomeScreen(navController, isGuest = false)
                }
                // Pantalla principal como Invitado
                composable("home/{isGuest}") { backStackEntry ->
                    val isGuest = backStackEntry.arguments?.getString("isGuest")?.toBoolean() ?: false
                    HomeScreen(navController, isGuest)
                }

                // Pantallas de la bottom navigation
                composable("categories") { CategoriesScreen(navController) }
                composable("blogs") { BlogScreen(navController) }
                composable("perfil") { PerfilScreen(navController) }


            }
        }
    }
}
