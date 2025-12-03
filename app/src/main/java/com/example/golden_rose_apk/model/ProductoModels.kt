package com.example.golden_rose_apk.model

data class ProductoDto(
    val id: Long?,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val rareza: String?,
    val rarezaIconUrl: String?,
    val imagenUrl: String?,
    val stock: Int?,
    val activo: Boolean? = null
)

data class ProductoRequest(
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int?,
    val rareza: String? = null,
    val rarezaIconUrl: String? = null,
    val imagenUrl: String? = null
)
