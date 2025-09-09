package com.example.desafio_mesas_comandas.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.components.SearchBarCustom
import com.example.desafio_mesas_comandas.components.TopBarCustom
import com.example.desafio_mesas_comandas.data.local.TableEntity
import com.example.desafio_mesas_comandas.ui.theme.Typography
import com.example.desafio_mesas_comandas.ui.theme.amarelo
import com.example.desafio_mesas_comandas.ui.theme.neutro
import com.example.desafio_mesas_comandas.ui.theme.verde
import com.example.desafio_mesas_comandas.ui.theme.vermelho
import com.example.desafio_mesas_comandas.utils.toBrazilianCurrencyFromCents
import com.example.desafio_mesas_comandas.viewmodel.MapViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            MapPage(onBackClick = { navController.popBackStack("HomePage", inclusive = false) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapPage(
    onBackClick: () -> Unit,
    viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

) {
    val mesas by viewModel.mesas.collectAsState(initial = emptyList())
    val lazyPagingItems = viewModel.tables.collectAsLazyPagingItems()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val selectedFilter by viewModel.selectedFilterName.collectAsState()
//    LaunchedEffect(mesas) {
//        println("DEBUG mesas carregadas: ${mesas.size}")
//    }

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
            .background(neutro)
            .fillMaxSize()
    ) {

        Filtros(
            selectedFilter = selectedFilter,
            onFilterChange = { viewModel.updateFilter(it) }
        )

//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 16.dp)
//        ) {
//            items(
//                count = lazyPagingItems.itemCount,
//                key = { index -> lazyPagingItems.peek(index)?.id ?: index }
//            ) { index ->
//                val mesa = lazyPagingItems[index]
//                mesa?.let {
//                    CardMesa(it)
//                }
//            }

        Box(modifier = Modifier.fillMaxSize()) {
            // Se o carregamento inicial falhar, mostre uma mensagem de erro
            if (lazyPagingItems.loadState.refresh is LoadState.Error) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Falha ao carregar os dados.\nVerifique sua conexão.",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 16.dp)
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = { index -> lazyPagingItems.peek(index)?.id ?: index }
                    ) { index ->
                        val mesa = lazyPagingItems[index]
                        mesa?.let {
                            CardMesa(it)
                        }
                    }

                    lazyPagingItems.loadState.apply {
                        when {
                            append is LoadState.Loading -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            append is LoadState.Error -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Button(onClick = { lazyPagingItems.retry() }) {
                                            Text("Tentar Novamente")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (lazyPagingItems.loadState.refresh is LoadState.Loading || isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

//            // Adiciona o indicador de carregamento no final da lista
//            lazyPagingItems.loadState.apply {
//                when {
//                    append is LoadState.Loading -> {
//                        item {
//                            Box(
//                                modifier = Modifier.fillMaxWidth().padding(16.dp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator()
//                            }
//                        }
//                    }
//                }
//            }
    }

//        if (isLoading) {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        } else {
//            mesas?.let {
//                MesasGrid(mesas = mesas)
//            }
//        }

    if (lazyPagingItems.loadState.refresh is LoadState.Loading || isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
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
fun MesasGrid(mesas: List<TableEntity>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        items(
            items = mesas,
            key = { mesa -> mesa.id }
        ) { mesa ->
            CardMesa(mesa)
        }
    }

}

@Composable
fun CardMesa(mesa: TableEntity) {
    val activityColor = mesa.activity
    val backgroundColors = when (activityColor) {
        "inactive" -> vermelho
        "active" -> verde
        "waiting" -> amarelo
        else -> Color.White

    }
    val hasContent = mesa.activity != "empty"

    Card(
        modifier = Modifier
            .width(110.dp)
            .height(116.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColors),
    ) {
        Box(Modifier) {
            Column(

            ) {
                Box(
                    Modifier
                        .padding(start = 12.dp)
                ) {
                    Text("${mesa.title}", style = Typography.labelMedium)
                }
                if (hasContent) {
                    Column(
                        Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 3.dp)
                    ) {
                        Row(
                            Modifier.padding(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (mesa.orderCount > 0) {
                                Icone(imageVector = ImageVector.vectorResource(id = R.drawable.receipt))
                                Text(
                                    mesa.orderCount.toString(),
                                    style = Typography.labelSmall
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            val customerName = mesa.customerName
                            val orderCount = mesa.orderCount
                            when {
                                orderCount == 1 && !customerName.isNullOrEmpty() -> {
                                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                                    Spacer(modifier = Modifier)
                                    Text(customerName, style = Typography.labelSmall)
                                }

                                orderCount > 1 -> {
                                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        orderCount.toString(),
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
                            val subtotal = mesa.subTotal

                            Icone(imageVector = ImageVector.vectorResource(id = R.drawable.paid))
                            Text(
                                "${subtotal?.toBrazilianCurrencyFromCents()}",
                                style = Typography.labelSmall
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val sellerName = mesa.sellerName
                            Icone(imageVector = ImageVector.vectorResource(id = R.drawable.room_service))
//                            when{
//                                sellerName.isEmpty(){
//                                    Text(
//                                        mesa.sellerName,
//                                        style = Typography.labelSmall
//                                    )
//                                }
//                            } else {
//                            Text(
//                                mesa.sellerName,
//                                style = Typography.labelSmall
//                            )
//                        }
                            val sellerNameToDisplay =
                                if (mesa.sellerName.isBlank()) "N/A" else mesa.sellerName

                            Text(
                                text = mesa.sellerName.ifBlank { "N/A" },
                                style = Typography.labelSmall,
                                maxLines = 1
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
