package com.example.golden_rose_apk.Screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.golden_rose_apk.Screens.HomeBottomNavigationBar
import com.example.golden_rose_apk.model.BottomNavItem
import com.example.golden_rose_apk.model.Skin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.golden_rose_apk.ViewModel.MarketplaceViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavController, marketplaceViewModel: MarketplaceViewModel = viewModel()) {
    val allSkins by marketplaceViewModel.skins.collectAsState()
    val searchQuery by marketplaceViewModel.searchQuery.collectAsState()
    val isLoading by marketplaceViewModel.isLoading.collectAsState()
    val error by marketplaceViewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(error) {
        error?.let { message ->
            scope.launch { snackbarHostState.showSnackbar(message) }
        }
    }

    val filteredSkins = allSkins.filter { it.name.contains(searchQuery, ignoreCase = true) }

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
                        text = "Categorías",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = { Spacer(modifier = Modifier.width(20.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5649A5),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            HomeBottomNavigationBar(navController = navController, navItems = navItems)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = marketplaceViewModel::onSearchQueryChange,
                label = { Text("Buscar por nombre") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )

            if (isLoading && allSkins.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredSkins) { skin ->
                    SkinCard(skin) { navController.navigate("productDetail/${skin.id}") }
                }

                if (filteredSkins.isEmpty() && searchQuery.isNotEmpty() && !isLoading) {
                    item {
                        Text(
                            "No encontramos resultados para \"$searchQuery\"",
                            modifier = Modifier.padding(vertical = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SkinCard(skin: Skin, onSkinClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSkinClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = "file:///android_asset/${skin.image}",
                contentDescription = skin.name,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(skin.name, fontWeight = FontWeight.Bold)
                Text(
                    skin.Type ?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "$${skin.price}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesScreenPreview() {
    CategoriesScreen(navController = rememberNavController())
}