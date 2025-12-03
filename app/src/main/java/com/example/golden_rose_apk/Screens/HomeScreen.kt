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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.filled.Notifications


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var searchText by remember { mutableStateOf("") }

    // Categorías principales
    val categories = listOf(
        Category("Meele", Color(0xFF5649A5)),
        Category("Vandal", Color(0xFF4CAF50)),
        Category("Operator", Color(0xFF2196F3)),
        Category("Phantom", Color(0xFFFF9800))
    )

    // Productos más vendidos
    val topProducts = listOf(
        Product("Vandal Reaver", "$199.99", Color(0xFFE3F2FD)),
        Product("Knife Champions", "$159.99", Color(0xFFF3E5F5)),
        Product("Marshal Forajida", "$89.99", Color(0xFFE8F5E8)),
        Product("Opeerator Chaocaos", "$299.99", Color(0xFFFFF3E0))
    )

    // Barra de navegación inferior
    val navItems = listOf(
        BottomNavItem("Inicio", Icons.Filled.Home),
        BottomNavItem("Categorías", Icons.Filled.Category),
        BottomNavItem("Blogs", Icons.Filled.Article),
        BottomNavItem("Perfil", Icons.Filled.Person)
    )
    var selectedNavItem by remember { mutableStateOf("Inicio") }
    Scaffold(
        topBar = {
            // TOP BAR CON MÚLTIPLES BOTONES
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Barra de búsqueda
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("¿Qué buscas?") },
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                            focusedLabelColor = Color.White.copy(alpha = 0.8f),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        },
                        
                        singleLine = true
                    )


                    // Botón de notificaciones (campana)
                    IconButton(
                        onClick = { /* Abrir filtros */ },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Filled.Notifications,
                            contentDescription = "Campanita",
                            tint = Color.White
                        )
                    }


                    // Botón de corazón (favoritos)
                    IconButton(
                        onClick = { /* Abrir filtros */ },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Campanita",
                            tint = Color.White
                        )
                    }

                    // Botón de carrito
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navController.navigate("cart") }
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White
                        )

                        // Badge del carrito
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(Color.Red, CircleShape)
                                .align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = "3",
                                color = Color.White,
                                fontSize = 8.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navItems = navItems,
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it }
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
                    modifier = Modifier.clickable { /* Navegar a productos */ }
                )
            }

            // Grid de productos más vendidos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(400.dp)
            ) {
                items(topProducts) { product ->
                    ProductCard(product = product)
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Espacio para bottom bar
        }
    }
}

@Composable
fun DownBar(navController: NavController) {

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF5649A5),
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            label = { Text("Inicio", color = if (currentRoute == "home") Color.White else Color.LightGray) },
            icon = { Icon(Icons.Default.Home, contentDescription = null, tint = if (currentRoute == "home") Color.White else Color.LightGray) }
        )
        NavigationBarItem(
            selected = currentRoute == "categorias",
            onClick = {
                navController.navigate("categorias") {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            label = { Text("Categorías", color = if (currentRoute == "categorias") Color.White else Color.LightGray) },
            icon = { Icon(Icons.Default.List, contentDescription = null, tint = if (currentRoute == "categorias") Color.White else Color.LightGray) }
        )
        NavigationBarItem(
            selected = currentRoute == "blogs",
            onClick = {
                navController.navigate("blogs") {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            label = { Text("Blogs", color = if (currentRoute == "blogs") Color.White else Color.LightGray) },
            icon = { Icon(Icons.Default.List, contentDescription = null, tint = if (currentRoute == "blogs") Color.White else Color.LightGray) }
        )
        NavigationBarItem(
            selected = currentRoute == "perfil",
            onClick = {
                navController.navigate("perfil") {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            label = { Text("Perfil", color = if (currentRoute == "perfil") Color.White else Color.LightGray) },
            icon = { Icon(Icons.Default.Person, contentDescription = null, tint = if (currentRoute == "perfil") Color.White else Color.LightGray) }
        )
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
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Ver detalles del producto */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Imagen del producto (placeholder)
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
                onClick = { /* Agregar al carrito */ },
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

// Barra de navegación inferior
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

// Data Classes
data class Category(
    val name: String,
    val color: Color
)

data class Product(
    val name: String,
    val price: String,
    val backgroundColor: Color
)

data class BottomNavItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}