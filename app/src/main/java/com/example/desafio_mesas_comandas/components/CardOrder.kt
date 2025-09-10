//package com.example.desafio_mesas_comandas.components
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.Card
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.desafio_mesas_comandas.utils.toBrazilianCurrencyFromCents
//import com.example.desafio_mesas_comandas.viewmodel.OrderViewModel
//import com.example.desafio_mesas_comandas.viewmodel.OrderViewModelFactory
//
//@Composable
//fun CardOrderInfo(modifier: Modifier = Modifier) {
//    val context = LocalContext.current.applicationContext
//    val viewModel: OrderViewModel = viewModel(
//        factory = OrderViewModelFactory(mesaId, context as android.app.Application)
//    )
//
//    LaunchedEffect(mesaId) {
//        viewModel.loadOrders()
//    }
//    val orders by viewModel.orders.collectAsState()
//    Card {
//        Column {
//            Text(
//                text = "${order.customerName ?: "Cliente"}",
//                modifier = Modifier.padding(8.dp)
//            )
//            Text(
//                text = " ${order.description}",
//                modifier = Modifier.padding(8.dp)
//            )
//            Text(
//                text = " ${order.subtotal.toBrazilianCurrencyFromCents()}",
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//
//    }
//}