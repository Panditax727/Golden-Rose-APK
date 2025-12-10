package com.example.golden_rose_apk.ViewModel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.golden_rose_apk.model.CartItemPayload
import com.example.golden_rose_apk.model.CreateOrderRequest
import com.example.golden_rose_apk.model.PaymentRequest
import com.example.golden_rose_apk.model.ProductFirestore
import kotlinx.coroutines.launch
import com.example.golden_rose_apk.repository.OrderRepository
import com.example.golden_rose_apk.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import kotlin.collections.filterNot
import kotlin.collections.map


data class CartItem(
    val product: ProductFirestore,
    val quantity: Int
)

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val orderRepository = OrderRepository()
    private val paymentRepository = PaymentRepository()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _checkoutState = MutableStateFlow<CheckoutState>(CheckoutState.Idle)
    val checkoutState: StateFlow<CheckoutState> = _checkoutState

    // Agregar producto desde la Firebase
    fun addToCart(product: ProductFirestore) {
        viewModelScope.launch {
            val current = _cartItems.value.toMutableList()
            val index = current.indexOfFirst { it.product.id == product.id }

            if (index >= 0) {
                val item = current[index]
                current[index] = item.copy(quantity = item.quantity + 1)
            } else {
                current.add(CartItem(product, 1))
            }

            _cartItems.value = current
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filterNot {
            it.product.id == productId
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }

        _cartItems.value = _cartItems.value.map {
            if (it.product.id == productId) it.copy(quantity = quantity)
            else it
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun checkout(total: Double, userId: String?, token: String?) {
        viewModelScope.launch {
            if (_cartItems.value.isEmpty()) return@launch

            if (userId.isNullOrBlank() || token.isNullOrBlank()) {
                _checkoutState.value = CheckoutState.Error("Debes iniciar sesión.")
                return@launch
            }

            _checkoutState.value = CheckoutState.Loading

            try {
                val items = _cartItems.value.map {
                    CartItemPayload(
                        productoId = it.product.id,
                        cantidad = it.quantity,
                        precioUnitario = it.product.price
                    )
                }

                val order = CreateOrderRequest(
                    usuarioId = userId,
                    items = items,
                    total = total
                )

                val orderResponse = orderRepository.createOrder(order, token)

                val payment = paymentRepository.createPayment(
                    PaymentRequest(
                        ordenId = orderResponse.id.toString(),
                        monto = total
                    ),
                    token
                )

                _checkoutState.value = CheckoutState.Success(
                    orderId = orderResponse.id?.toString(),
                    paymentId = payment.id,
                    paymentLink = payment.enlacePago
                )

                clearCart()
            } catch (e: IOException) {
                _checkoutState.value = CheckoutState.Error("Error de red.")
            } catch (e: Exception) {
                Log.e("CartViewModel", "Checkout error", e)
                _checkoutState.value =
                    CheckoutState.Error("Error procesando el pago.")
            }
        }
    }

    fun resetCheckoutState() {
        _checkoutState.value = CheckoutState.Idle
    }
}


class CartViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(application) as T
    }
}

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