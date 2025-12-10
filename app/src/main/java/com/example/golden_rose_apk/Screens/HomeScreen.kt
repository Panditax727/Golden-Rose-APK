package com.example.golden_rose_apk.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.golden_rose_apk.model.BottomNavItem
import com.example.golden_rose_apk.model.Category
import com.example.golden_rose_apk.model.Product
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.golden_rose_apk.ViewModel.CartViewModel
import com.example.golden_rose_apk.ViewModel.MarketplaceViewModel
import com.example.golden_rose_apk.config.Constants
import com.example.golden_rose_apk.model.Skin
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    isGuest: Boolean,
    marketplaceViewModel: MarketplaceViewModel,
    cartViewModel: CartViewModel
) {
    val greeting = if (isGuest) {
        "Bienvenido Invitado a Golden Rose"
    } else {
        "Bienvenido a Golden Rose"
    }
    val skins by marketplaceViewModel.skins.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        marketplaceViewModel.refresh()  // o la función que cargue las skins
    }

    var searchText by remember { mutableStateOf("") }

    // Snackbar Mostrar mensaje
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Categorías principales
    val categories = listOf(
        Category("Meele", Color(0xFF5649A5)),
        Category("Vandal", Color(0xFF4CAF50)),
        Category("Operator", Color(0xFF2196F3)),
        Category("Phantom", Color(0xFFFF9800))
    )


    // Barra de navegación inferior
    val navItems = listOf(
        BottomNavItem("Inicio", Icons.Filled.Home, "home"),
        BottomNavItem("Categorías", Icons.Filled.Category, "categories"),
        BottomNavItem("Blogs", Icons.Filled.Article, "blogs"),
        BottomNavItem("Perfil", Icons.Filled.Person, "perfil")
    )


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            // TOP BAR CON MÚLTIPLES BOTONES - VERSIÓN MEJORADA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color(0xFF5649A5))
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp) // Un poco más de espacio
                ) {
                    // Barra de búsqueda - MEJORADA
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = {
                            Text(
                                "¿Qué buscas?",
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                            focusedLabelColor = Color.White.copy(alpha = 0.9f), // Más visible
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.White.copy(alpha = 0.1f), // Fondo sutil
                            unfocusedContainerColor = Color.White.copy(alpha = 0.05f)
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Buscar",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.clickable {
                                    navController.navigate("search")
                                }
                            )
                        },

                        singleLine = true,
                        placeholder = {
                            Text(
                                "Buscar productos...",
                                color = Color.White.copy(alpha = 0.5f) // Placeholder visible
                            )
                        }
                    )

                    // Botón de notificaciones (campana) - MEJORADO
                    Box(
                        modifier = Modifier.size(42.dp) // Un poco más grande
                    ) {
                        IconButton(
                            onClick = {  },
                            modifier = Modifier.size(42.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(
                                Icons.Filled.Notifications,
                                contentDescription = "Notificaciones",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp) // Icono más grande
                            )
                        }
                    }

                    // Botón de corazón (favoritos) - MEJORADO
                    if (!isGuest) {
                        IconButton(
                            onClick = { navController.navigate("favorites") },
                            modifier = Modifier.size(42.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Favoritos",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Botón de carrito - MEJORADO
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clickable { navController.navigate("cart") }
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White,
                            modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.Center)
                        )

                        // Badge del carrito - MEJORADO
                        //Box(
                            //modifier = Modifier
                                //.size(18.dp) // Un poco más grande
                                //.background(
                                   // Color(0xFFFF5252), // Rojo más vibrante
                                   // CircleShape
                                //)
                               // .align(Alignment.TopEnd)
                       // ) {
                            //Text(
                               // text = "3",
                               // color = Color.White,
                               // fontSize = 9.sp, // Un poco más grande
                               // fontWeight = FontWeight.Bold, // Negrita
                                //modifier = Modifier.align(Alignment.Center)
                            //)
                        //}
                    }
                }
            }
        },
        bottomBar = {
            HomeBottomNavigationBar(
                navController = navController,
                navItems = navItems
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Text(
                    text = "Bienvenido a Golden Rose",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5649A5)
                )
            }

            // Categorías principales
            Text(
                text = "Categorías Destacadas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    CategoryCard(category = category)
                }
            }

            // Lo más vendido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lo más vendido",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Ver todo →",
                    fontSize = 14.sp,
                    color = Color(0xFF5649A5),
                    modifier = Modifier.clickable { navController.navigate("all_products") }
                )
            }

            // Grid de productos más vendidos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(400.dp)
            ) {
                items(skins) { skin ->
                    ProductCardFromSkin(
                        skin = skin,
                        navController = navController,
                        cartViewModel = cartViewModel,
                        onAddToCart = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "✅ ${skin.name} agregada al carrito",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        onClick = {
                            navController.navigate("productDetail/${skin.id}")
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// Componente de tarjeta de categoría
@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable { /* Navegar a categoría */ },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = category.color.copy(alpha = 0.1f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(category.color.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Diamond,
                        contentDescription = category.name,
                        tint = category.color,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

// Componente de tarjeta de producto
@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("product_detail/${product.name}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(product.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Diamond,
                    contentDescription = product.name,
                    tint = Color(0xFF5649A5),
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.price,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5649A5),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("cart") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5649A5)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Agregar")
            }
        }
    }
}

// Barra de navegación inferior ESPECÍFICA para HomeScreen
@Composable
fun HomeBottomNavigationBar(
    navController: NavController,
    navItems: List<BottomNavItem>
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route ||
                        (item.route == "home" && currentRoute?.startsWith("home/") == true),
                onClick = {
                    // Navegación simple y directa
                    if (item.route == "home") {
                        navController.navigate("home/false") {
                            launchSingleTop = true
                            // Limpiar pila para evitar múltiples homes
                            popUpTo("home") { inclusive = true }
                        }
                    } else {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    val isSelected = currentRoute == item.route ||
                            (item.route == "home" && currentRoute?.startsWith("home/") == true)
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) Color(0xFF5649A5) else Color.Gray
                    )
                },
                label = {
                    val isSelected = currentRoute == item.route ||
                            (item.route == "home" && currentRoute?.startsWith("home/") == true)
                    Text(
                        text = item.title,
                        color = if (isSelected) Color(0xFF5649A5) else Color.Gray
                    )
                }
            )
        }
    }
}
// Componente reutilizable para badge de notificaciones
@Composable
fun NotificationBadge(count: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(40.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Notificaciones",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        if (count > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Red, CircleShape)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = if (count > 9) "9+" else count.toString(),
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
fun ProductCardFromSkin(
    skin: Skin,
    navController: NavController,
    cartViewModel: CartViewModel,
    onAddToCart: () -> Unit,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                println("📍 Navegando a detalle de skin ID: ${skin.id}")
                navController.navigate("product_detail/${skin.id}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Manejar diferentes formatos de imagen
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))

            if (skin.hasImageData) {
                // Si tiene datos de imagen desde API
                AsyncImage(
                    model = Constants.productoImagenEndpoint(skin.id.toString()),
                    contentDescription = skin.name,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            } else if (skin.imageUrl?.isNotEmpty() == true) {
                // Si tiene URL de imagen
                AsyncImage(
                    model = skin.imageUrl,
                    contentDescription = skin.name,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop
                )
            } else if (skin.image?.isNotEmpty() == true) {
                // Si tiene imagen en assets
                AsyncImage(
                    model = "file:///android_asset/${skin.image}",
                    contentDescription = skin.name,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop,
                )
            } else {
                // Imagen por defecto
                Box(
                    modifier = imageModifier
                        .background(Color(0xFF5649A5).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Diamond,
                        contentDescription = skin.name,
                        tint = Color(0xFF5649A5),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = skin.name ?: "Sin nombre",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${skin.price ?: "0.00"}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5649A5),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    println("➕ Agregando al carrito: ${skin.name}")
                    cartViewModel.addToCart(skin)
                    onAddToCart()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5649A5)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Agregar al carrito", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CartBadge(count: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.size(40.dp)) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                Icons.Outlined.ShoppingCart,
                contentDescription = "Carrito",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        if (count > 0) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(Color(0xFFFF9800), CircleShape)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = if (count > 9) "9+" else count.toString(),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}




