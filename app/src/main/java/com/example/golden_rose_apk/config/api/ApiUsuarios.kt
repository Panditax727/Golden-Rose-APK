package com.example.golden_rose_apk.config.api

import com.example.golden_rose_apk.config.Constants.BASE_USUARIO
import com.example.golden_rose_apk.model.Usuarios
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiUsuarios {
    @GET("usuarios") // Endpoint de tu backend
    suspend fun getUsuarios(): List<Usuarios>
}

object RetrofitInstance {
    val api: ApiUsuarios by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_USUARIO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiUsuarios::class.java)
    }
}