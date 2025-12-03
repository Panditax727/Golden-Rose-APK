package com.example.golden_rose_apk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navArgument
import kotlinx.coroutines.flow.firstOrNull
import com.example.golden_rose_apk.Screens.HomeScreen
import com.example.golden_rose_apk.Screens.LoginScreen
import com.example.golden_rose_apk.Screens.RegisterScreen
import com.example.golden_rose_apk.Screens.WelcomeScreen
import com.example.golden_rose_apk.Screens.CartScreen
import com.example.golden_rose_apk.Screens.ProductDetailScreen
import com.example.golden_rose_apk.Screens.ProductFormScreen
import com.example.golden_rose_apk.data.local.SessionManager
import com.example.golden_rose_apk.data.remote.ApiClient
import com.example.golden_rose_apk.data.remote.ExternalApiClient
import com.example.golden_rose_apk.data.repository.AppRepository
import com.example.golden_rose_apk.data.repository.ValorantSkinRepository
import com.example.golden_rose_apk.viewmodel.AppViewModelFactory
import com.example.golden_rose_apk.viewmodel.AuthViewModel
import com.example.golden_rose_apk.viewmodel.CartViewModel
import com.example.golden_rose_apk.viewmodel.ProductViewModel
import com.example.golden_rose_apk.viewmodel.ValorantSkinViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.golden_rose_apk.data.local.PreferencesStore
import com.example.golden_rose_apk.viewmodel.SettingsViewModel

/**
 * Punto de entrada Compose: configura repositorios, ViewModels y navegación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val session = remember { SessionManager(this@MainActivity) }
            val prefs = remember { PreferencesStore(this@MainActivity) }
            val api = remember { ApiClient.create { session.tokenFlow.firstOrNull() } }
            val repo = remember { AppRepository(api) }
            val skinRepo = remember { ValorantSkinRepository(ExternalApiClient.valorantApi()) }
            val factory = remember { AppViewModelFactory(repo, session, skinRepo, prefs) }

            val authVm: AuthViewModel = viewModel(factory = factory)
            val productVm: ProductViewModel = viewModel(factory = factory)
            val cartVm: CartViewModel = viewModel(factory = factory)
            val skinVm: ValorantSkinViewModel = viewModel(factory = factory)
            val settingsVm: SettingsViewModel = viewModel(factory = factory)

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "welcome") {
                composable("welcome") { WelcomeScreen(navController) }
                composable("login") { LoginScreen(navController, authVm) }
                composable("register") { RegisterScreen(navController, authVm) }
                composable("home") { HomeScreen(navController, authVm, productVm, cartVm) }
                composable("cart") { CartScreen(navController, authVm, cartVm) }
                composable(
                    "productDetail/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.LongType })
                ) { backStack ->
                    val id = backStack.arguments?.getLong("productId") ?: 0L
                    ProductDetailScreen(navController, id, productVm, cartVm, authVm)
                }
                composable("productForm") {
                    ProductFormScreen(navController, null, productVm, skinVm)
                }
                composable(
                    "productForm/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.LongType })
                ) { backStack ->
                    val id = backStack.arguments?.getLong("productId")
                    ProductFormScreen(navController, id, productVm, skinVm)
                }
                composable("settings") { SettingsScreen(navController, authVm, settingsVm) }
            }
        }
    }
}   
