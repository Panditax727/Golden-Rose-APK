package com.example.golden_rose_apk.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.golden_rose_apk.model.ProductoDto
import com.example.golden_rose_apk.repository.SkinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MarketplaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkinRepository(application)
    private val _skins = MutableStateFlow<List<ProductoDto>>(emptyList())
    val skins: StateFlow<List<ProductoDto>> = _skins


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    init {
        loadSkins()
    }

    private fun loadSkins() {
        _skins.value = repository.getSkins()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun getSkinById(id: Int): ProductoDto? {
        return _skins.value.find { it.id == id.toLong() }
    }
}


// --- FÁBRICA DEL VIEWMODEL ---
// Esto es necesario para poder pasar el 'Application' al ViewModel
class MarketplaceViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketplaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketplaceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}