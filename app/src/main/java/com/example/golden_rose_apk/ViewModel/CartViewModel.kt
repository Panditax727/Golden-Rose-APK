package com.example.golden_rose_apk.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.golden_rose_apk.config.api.RetrofitInstance
import com.example.golden_rose_apk.model.AddCartRequest
import com.example.golden_rose_apk.model.CartItemDto
import com.example.golden_rose_apk.model.OrderRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cartItems: StateFlow<List<CartItemDto>> get() = _cartItems

    init {
        cargarCarrito()
    }

    fun cargarCarrito() {
        viewModelScope.launch {
            try {
                val carrito = RetrofitInstance.cartApi.obtenerCarrito()
                _cartItems.value = carrito
            } catch (e: Exception) {
                println("Error al cargar carrito: ${e.message}")
            }
        }
    }

    fun updateQuantity(productoId: Long, nuevaCantidad: Int) {
        viewModelScope.launch {
            try {
                if (nuevaCantidad < 1) return@launch
                val item = _cartItems.value.find { it.productoId == productoId } ?: return@launch
                RetrofitInstance.cartApi.agregar(AddCartRequest(productoId, nuevaCantidad))
                // Recargar carrito actualizado
                cargarCarrito()
            } catch (e: Exception) {
                println("Error al actualizar cantidad: ${e.message}")
            }
        }
    }

    fun removeFromCart(productoId: Long) {
        viewModelScope.launch {
            try {
                // Suponiendo que tu API tiene un endpoint DELETE, si no solo actualizar con cantidad 0
                RetrofitInstance.cartApi.agregar(AddCartRequest(productoId, 0))
                cargarCarrito()
            } catch (e: Exception) {
                println("Error al eliminar item: ${e.message}")
            }
        }
    }

    fun checkout(direccion: String) {
        viewModelScope.launch {
            try {
                val orden = RetrofitInstance.cartApi.checkout(OrderRequest(direccion))
                println("Orden creada: $orden")
                // Vaciar carrito después del checkout
                cargarCarrito()
            } catch (e: Exception) {
                println("Error al hacer checkout: ${e.message}")
            }
        }
    }
}