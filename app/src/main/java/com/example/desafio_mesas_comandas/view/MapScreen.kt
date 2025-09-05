package com.example.desafio_mesas_comandas.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.components.SearchBarCustom
import com.example.desafio_mesas_comandas.components.TopBarCustom
import com.example.desafio_mesas_comandas.data.model.Checkpad
import com.example.desafio_mesas_comandas.data.model.CheckpadApiResponse
import com.example.desafio_mesas_comandas.ui.theme.Typography
import com.example.desafio_mesas_comandas.ui.theme.amarelo
import com.example.desafio_mesas_comandas.ui.theme.neutro
import com.example.desafio_mesas_comandas.ui.theme.verde
import com.example.desafio_mesas_comandas.ui.theme.vermelho
import com.example.desafio_mesas_comandas.viewmodel.MapViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            MapPage(onBackClick = { navController.popBackStack() })
        }
    }
}

//@Composable
//fun MapScreen(navController: NavController) {
//    MapPage(onBackClick = { navController.popBackStack() })
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapPage(
    onBackClick: () -> Unit,
    viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val mesas by viewModel.mesas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    Column {
        TopBarCustom("Mapa de atendimento", onBackClick)
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
        )

        SearchBarCustom(
            value = searchText,
            onValueChange = { viewModel.updateSearch(it) },
        )

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
        )

    }
    Column(
        modifier = Modifier
            .background(neutro).padding(bottom = 48.dp)
    ) {

        Filtros(
            selectedFilter = selectedFilter,
            onFilterChange = { viewModel.updateFilter(it) }
        )


        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            mesas?.let {
                MesasGrid(mesas = it)
            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filtros(
    selectedFilter: String,
    onFilterChange: (String) -> Unit
) {
    val filtros = listOf("Visão Geral", "Em Atendimento", "Ociosas", "Disponíveis", "Sem Pedidos")
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(filtros) { item ->
            val isSelected = (item == selectedFilter)

            FilterChip(
                selected = (item == selectedFilter),
                onClick = { onFilterChange(item) },
                label = {
                    Text(item)
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White,
                    selectedContainerColor = Color.Black,
                    labelColor = Color.Black,
                    selectedLabelColor = Color.White,
                ),
                shape = FilterChipDefaults.shape
            )
        }
    }
}

@Composable
fun MesasGrid(mesas: CheckpadApiResponse, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        mesas.checkpads?.let { checkpadsList ->
            items(
                items = checkpadsList,
                key = { checkpad -> checkpad.id }
            ) { checkpad ->
                CardMesa(checkpad)
            }
        }
    }
}


@Composable
fun CardMesa(mesa: Checkpad) {
    val Activitycolor = mesa.activity
    val backgroundColors = when (Activitycolor) {
        "inactive" -> vermelho
        "active" -> verde
        "waiting" -> amarelo
        else -> Color.White

    }
    Card(
        modifier = Modifier
            .width(110.dp)
            .height(116.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColors),
    ) {
        Box(Modifier) {
            Column {
                Box(
                    Modifier
                        .padding(start = 12.dp)
                ) {
                    Text("${mesa.title}", style = Typography.labelMedium)
                }

                Box(
                    Modifier
                        .width(84.dp)
                        .height(60.dp)
                        .padding(start = 8.dp, end = 8.dp)
//                        .background(Color.Blue)
                ) {
                    Column {
                        Row(
                            Modifier.padding(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (mesa.orderSheets.isNotEmpty()) {
                                Icone(imageVector = ImageVector.vectorResource(id = R.drawable.receipt))
                                Text(
                                    mesa.orderSheets.size.toString(),
                                    style = Typography.labelSmall
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            val numberOfCostumers =
                                mesa.orderSheets.firstOrNull()?.numberOfCustomers ?: 0
                            val customerName = mesa.orderSheets.firstOrNull()?.customerName

                            when {
                                numberOfCostumers == 1 && !customerName.isNullOrEmpty() -> {
                                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(customerName, style = Typography.labelSmall)
                                }

                                numberOfCostumers > 1 -> {
                                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        numberOfCostumers.toString(),
                                        style = Typography.labelSmall
                                    )
                                }

                                else -> {
                                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("0", style = Typography.labelSmall)
                                }
                            }


                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icone(imageVector = ImageVector.vectorResource(id = R.drawable.schedule))
                            Text("${mesa.idleTime} mins", style = Typography.labelSmall)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val subtotal = mesa.orderSheets.firstOrNull()?.subtotal

                            Icone(imageVector = ImageVector.vectorResource(id = R.drawable.paid))
                            Text("R$ " + subtotal, style = Typography.labelSmall)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icone(imageVector = ImageVector.vectorResource(id = R.drawable.room_service))
                            Text(
                                "${mesa.orderSheets.firstOrNull()?.seller?.name}",
                                style = Typography.labelSmall
                            )
                        }
                    }

                }

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MapaAtendimentoPreview() {
    Column {
        MapPage(onBackClick = {})
    }
}
