package com.example.golden_rose_apk.config.api

import com.example.golden_rose_apk.model.BlogPost
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BlogApi {
    @GET("blogs") // Endpoint de tu backend
    suspend fun getBlogs(): List<BlogPost>
}

object RetrofitInstance {
    val api: BlogApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://tu-backend.com/api/") // Cambia a tu URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogApi::class.java)
    }
}