package com.example.golden_rose_apk.Screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.golden_rose_apk.model.CheckoutItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController) {
    // Datos de ejemplo (simulación sin carrito)
    val checkoutItems = listOf(
        CheckoutItem("Skin Rosa", 1, 4990.0),
        CheckoutItem("Skin Azul", 2, 3990.0),
        CheckoutItem("Skin Dorada", 1, 6990.0)
    )

    // Cálculos
    val subtotal = checkoutItems.sumOf { it.price * it.quantity }
    val commission = subtotal * 0.05
    val shipping = if (subtotal > 0) 1490.0 else 0.0
    val total = subtotal + commission + shipping

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmar Compra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Resumen del Pedido", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(checkoutItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.name} (x${item.quantity})")
                        Text("$${(item.price * item.quantity).formatPrice()}")
                    }
                }
            }

            Divider()
            SummaryRow("Subtotal:", subtotal)
            SummaryRow("Envío:", shipping)
            SummaryRow("Comisión:", commission)
            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("$${total.formatPrice()}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Acción simulada
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Pagar")
            }
        }
    }
}

fun Double.formatPrice(): String {
    return String.format("%.0f", this)
}

@Composable
fun SummaryRow(label: String, value: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text("$${value.formatPrice()}", style = MaterialTheme.typography.bodyMedium)
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(navController = rememberNavController())
}
