package com.example.desafio_mesas_comandas.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.desafio_mesas_comandas.view.components.TopBarCustom
import com.example.desafio_mesas_comandas.ui.theme.Typography
import com.example.desafio_mesas_comandas.ui.theme.neutro
import com.example.desafio_mesas_comandas.utils.toBrazilianCurrencyFromCents
import com.example.desafio_mesas_comandas.viewmodel.OrderViewModel
import com.example.desafio_mesas_comandas.viewmodel.OrderViewModelFactory

@Composable
fun OrderPage(navController: NavHostController, mesaId: Int) {
    val context = LocalContext.current.applicationContext
    val viewModel: OrderViewModel = viewModel(
        factory = OrderViewModelFactory(mesaId, context as android.app.Application)
    )

    LaunchedEffect(mesaId) {
        viewModel.loadOrders()
    }

    val orders by viewModel.orders.collectAsState()

    Scaffold(
        topBar = {
            TopBarCustom(
                title = "Pedidos da mesa",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (orders.isEmpty()) {
                    Row(Modifier.padding(20.dp)) {
                        Text("Mesa Está Vazia", style = Typography.titleLarge)
                    }


            } else {
                orders.forEach { order ->
                    Card(
                        backgroundColor = neutro,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 8.dp)
                    ) {
                        Column {
                            val customer =
                                if (order.customerName.isNullOrBlank()) "Não informado" else order.customerName
                            val description =
                                if (order.description.isNullOrBlank()) "Sem descrição" else order.description

                            Text(
                                text = "Nome Cliente: ${customer}", style = Typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Descrição: ${description}", style = Typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Subtotal: ${order.subtotal.toBrazilianCurrencyFromCents()}",
                                style = Typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}
