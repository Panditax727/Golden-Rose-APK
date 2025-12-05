package com.example.golden_rose_apk

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.golden_rose_apk.Screens.blogs.BlogScreen
import com.example.golden_rose_apk.Screens.categories.CategoriesScreen
import com.example.golden_rose_apk.Screens.HomeScreen
import com.example.golden_rose_apk.Screens.LoginScreen
import com.example.golden_rose_apk.Screens.MarketplaceScreen
import com.example.golden_rose_apk.Screens.perfil.PerfilScreen
import com.example.golden_rose_apk.Screens.RegisterScreen
import com.example.golden_rose_apk.Screens.WelcomeScreen
import com.example.golden_rose_apk.Screens.blogs.BlogDetailScreen
import com.example.golden_rose_apk.Screens.cart.CartScreen
import com.example.golden_rose_apk.Screens.cart.CheckoutScreen
import com.example.golden_rose_apk.Screens.cart.PaymentSuccessScreen
import com.example.golden_rose_apk.Screens.favorites.FavoritesScreen
import com.example.golden_rose_apk.Screens.products.ProductDetailScreen
import com.example.golden_rose_apk.ViewModel.AuthViewModel
import com.example.golden_rose_apk.ViewModel.BlogViewModel
import com.example.golden_rose_apk.ViewModel.CartViewModel
import com.example.golden_rose_apk.ViewModel.MarketplaceViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar edge-to-edge (opcional pero recomendado para diseño moderno)
        enableEdgeToEdge()

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
                // TopBar

                // Pantallas de la bottom navigation
                composable("categories") { CategoriesScreen(navController) }
                composable("blogs") { BlogScreen(navController) }
                composable(
                    "blogDetail/{blogId}"
                ) { backStackEntry ->
                    val blogId = backStackEntry.arguments?.getString("blogId")?.toIntOrNull() ?: 0
                    val blogViewModel: BlogViewModel = viewModel()
                    BlogDetailScreen(navController, blogId, blogViewModel)
                }

                composable("perfil") { PerfilScreen(navController) }

                // ========== PANTALLAS DE PRODUCTOS ==========
                composable("marketplace") {
                    val marketplaceViewModel: MarketplaceViewModel = viewModel()
                    MarketplaceScreen(navController, marketplaceViewModel)
                }

                // Pantalla de detalle de producto
                composable("productDetail/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments
                        ?.getString("productId")
                        ?: ""
                    val cartViewModel: CartViewModel = viewModel()
                    val marketplaceViewModel: MarketplaceViewModel = viewModel()

                    ProductDetailScreen(
                        navController = navController,
                        productId = productId,
                        marketplaceViewModel = marketplaceViewModel,
                        cartViewModel = cartViewModel
                    )
                }

                // ========== PANTALLAS DE FAVORITOS ==========
                composable("favorites") { FavoritesScreen(navController) }

                // ========== PANTALLAS DEL CARRITO Y CHECKOUT ==========
                composable("cart") {
                    val cartViewModel: CartViewModel = viewModel()
                    CartScreen(navController, cartViewModel)
                }


                composable("checkout") {
                    val cartViewModel: CartViewModel = viewModel()
                    val authViewModel: AuthViewModel = viewModel()
                    CheckoutScreen(
                        navController = navController
                    )
                }

                composable("payment_success/{orderId}") { backStackEntry ->
                    val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                    PaymentSuccessScreen(
                        navController = navController,
                        orderId = orderId
                    )
                }




            }
        }
    }
}
