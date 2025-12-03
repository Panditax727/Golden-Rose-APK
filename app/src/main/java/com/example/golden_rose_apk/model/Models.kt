package com.example.golden_rose_apk.model

import androidx.compose.ui.graphics.vector.ImageVector

data class LoginRequest(
    val email:  String,
    val password: String
)

data class LoginResponse(
    val token: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

data class Category(
    val name: String,
    val color: androidx.compose.ui.graphics.Color
)

data class Product(
    val name: String,
    val price: String,
    val backgroundColor: androidx.compose.ui.graphics.Color
)

data class CheckoutItem(
    val name: String,
    val quantity: Int,
    val price: Double
)

data class BlogPost(
    val id: Int,
    val title: String,
    val date: String,
    val preview: String,
    val content: String
)

