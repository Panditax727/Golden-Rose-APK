package com.example.golden_rose_apk.model

data class CartItemDto(
    val id: Long?,
    val productoId: Long,
    val nombre: String?,
    val cantidad: Int,
    val precioUnitario: Double?
)

data class AddCartRequest(
    val productoId: Long,
    val cantidad: Int
)

data class OrderRequest(
    val direccion: String,
    val metodoPago: String = "CARD"
)

data class OrderResponse(
    val id: Long?,
    val total: Double?,
    val estado: String?
)
