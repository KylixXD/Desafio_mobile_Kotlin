package com.example.desafio_mesas_comandas.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.desafio_mesas_comandas.viewmodel.MapViewModel


@Composable
fun MapScreen(navController: NavController) {
    MapPage(onBackClick = { navController.popBackStack() })
}

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

    Scaffold(topBar = {
        TopBarCustom("Mapa de atendimento", onBackClick)
    }) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

            SearchBarCustom(value = searchText, onValueChange = { viewModel.updateSearch(it) })
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

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


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filtros(
    selectedFilter: String,
    onFilterChange: (String) -> Unit
) {
    val filtros = listOf("Visão Geral", "Em Atendimento", "Ociosas", "Disponíveis")


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
    Card(
        modifier = Modifier
            .width(109.33.dp)
            .height(116.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray),
    ) {
        Box(Modifier.fillMaxSize()) {
            Column {
                Text("${mesa.title}")

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                    Text(mesa.orderSheets.firstOrNull()?.numberOfCustomers.toString()  )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.schedule))
                    Text("${mesa.idleTime} mins")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.paid))
                    Text("R$ ${mesa.orderSheets.firstOrNull()?.subtotal}")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.room_service))
                    Text("${mesa.orderSheets.firstOrNull()?.seller?.name}")
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

