package com.example.golden_rose_apk.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golden_rose_apk.config.api.RetrofitInstance
import com.example.golden_rose_apk.model.AddCartRequest
import com.example.golden_rose_apk.model.CartItemDto
import com.example.golden_rose_apk.model.OrderRequest
import kotlinx.coroutines.launch
import com.example.golden_rose_apk.model.Skin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Modelo local para el carrito
data class CartItem(
    val skin: Skin,
    var quantity: Int
)

// Estados del proceso de checkout
sealed class CheckoutState {
    object Idle : CheckoutState()
    object Loading : CheckoutState()
    data class Success(
        val orderId: String?,
        val paymentId: String?,
        val paymentLink: String?
    ) : CheckoutState()
    data class Error(val message: String) : CheckoutState()
}

class CartViewModel() : ViewModel(){

    private val _cartItems = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cartItems: StateFlow<List<CartItemDto>> get() = _cartItems

    init {
        cargarCarrito()
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            try {
                println("🛒 Intentando agregar producto ID: $productId")
                RetrofitInstance.cartApi.agregar(AddCartRequest(productId, 1))
                cargarCarrito()
                println("✅ Producto agregado exitosamente")
            } catch (e: Exception) {
                println("❌ Error al agregar al carrito: ${e.message}")
                e.printStackTrace()
            }
        }
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

    fun updateQuantity(productoId: String, nuevaCantidad: Int) {
        viewModelScope.launch {
            try {
                if (nuevaCantidad < 1) return@launch

                // Convertir String a Long para comparar
                val productoIdLong = productoId.toLongOrNull() ?: return@launch

                val item = _cartItems.value.find { it.productoId == productoIdLong } ?: return@launch
                RetrofitInstance.cartApi.agregar(AddCartRequest(productoId, nuevaCantidad))
                cargarCarrito()
            } catch (e: Exception) {
                println("Error al actualizar cantidad: ${e.message}")
            }
        }
    }

    fun removeFromCart(productoId: String) {
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