package com.example.golden_rose_apk.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.golden_rose_apk.model.Skin
import com.example.golden_rose_apk.repository.SkinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketplaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SkinRepository(application)

    // Lista de skins/productos
    private val _skins = MutableStateFlow<List<Skin>>(emptyList())
    val skins: StateFlow<List<Skin>> get() = _skins

    // Query de búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        loadSkins()
    }

    fun refresh() {
        loadSkins()
    }


    // Cambiar query de búsqueda
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    // Obtener skins desde el repositorio
    private fun loadSkins() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _skins.value = repository.getSkins()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obtener un Skin por id
    fun getSkinById(id: Int): Skin? {
        return _skins.value.find { it.id.toInt() == id }
    }
}

// --- Fábrica del ViewModel (opcional) ---
class MarketplaceViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketplaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketplaceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}