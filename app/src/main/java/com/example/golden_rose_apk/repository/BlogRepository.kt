package com.example.golden_rose_apk.repository


import com.example.golden_rose_apk.model.BlogPost

object BlogRepository {
    fun getBlogs(): List<BlogPost> {
        return listOf(
            BlogPost(1, "Primer Blog", "03/12/2025", "Este es un preview del primer blog...", "Contenido completo del primer blog"),
            BlogPost(2, "Segundo Blog", "02/12/2025", "Preview del segundo blog...", "Contenido completo del segundo blog"),
            BlogPost(3, "Tercer Blog", "01/12/2025", "Preview del tercer blog...", "Contenido completo del tercer blog"),
        )
    }
}