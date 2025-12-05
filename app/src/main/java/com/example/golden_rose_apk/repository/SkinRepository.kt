package com.example.golden_rose_apk.repository

import android.content.Context
import com.example.golden_rose_apk.model.ProductoDto
import com.example.golden_rose_apk.model.Skin
import com.example.golden_rose_apk.service.ProductApiService.catalogoApi
import com.example.golden_rose_apk.service.ProductApiService.productoApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SkinRepository(private val context: Context) {

    suspend fun getSkins(): List<Skin> = withContext(Dispatchers.IO) {
        // Primero intenta microservicio de productos; luego catalogo; ultimo recurso: assets locales
        try {
            return@withContext productoApi.getProducts()
        } catch (_: Exception) {
        }
        try {
            return@withContext catalogoApi.getProducts()
        } catch (_: Exception) {
        }
        return@withContext loadFromAssets()
    }

    private fun loadFromAssets(): List<Skin> {
        return try {
            val jsonString = context.applicationContext.assets.open("data/skins.json")
                .bufferedReader().use { it.readText() }
            val listSkinType = object : TypeToken<List<Skin>>() {}.type
            Gson().fromJson(jsonString, listSkinType)
        } catch (_: IOException) {
            emptyList()
        }
    }
}