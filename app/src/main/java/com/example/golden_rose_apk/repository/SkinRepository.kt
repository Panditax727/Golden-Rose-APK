package com.example.golden_rose_apk.repository

import android.content.Context
import com.example.golden_rose_apk.model.ProductoDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class SkinRepository(private val context: Context) {

    fun getSkins(): List<ProductoDto> {
        val jsonString: String
        try {

            jsonString = context.applicationContext.assets.open("data/skins.json")
                .bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        val listSkinType = object : TypeToken<List<ProductoDto>>() {}.type
        return Gson().fromJson(jsonString, listSkinType)
    }
}