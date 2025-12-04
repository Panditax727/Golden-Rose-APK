package com.example.golden_rose_apk.config.api

import com.example.golden_rose_apk.config.Constants.BASE_PRODUCTOS
import com.example.golden_rose_apk.model.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiProductos {
    @GET("productos") // Endpoint de tu backend
    suspend fun getProductos(): List<Product>
}

object RetrofitInstance {
    val api: ApiProductos by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_PRODUCTOS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiProductos::class.java)
    }
}