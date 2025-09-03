package com.example.desafio_mesas_comandas.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.ui.theme.laranja

@Composable
fun MapScreen(navController: NavController) {
    ParteCimaApp(onBackClick = { navController.popBackStack() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParteCimaApp(onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Mapa de atendimento") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back_black_24dp_1),
                        contentDescription = "Mapa de atendimento",
                        tint = laranja
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        var searchText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Vendedor, mesa, comanda, atendente", fontSize = 15.sp) },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
                    contentDescription = "Buscar",
                    tint = laranja
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
//                focusedPlaceholderColor = Color.Transparent,
//                unfocusedPlaceholderColor = Color.Transparent,
//                disabledPlaceholderColor = Color.Transparent
            )
        )
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        Filtros()
        MesasGrid()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filtros(modifier: Modifier = Modifier) {
    val items = listOf("Visão Geral", "Em Atendimento", "Ociosas", "Disponíveis")
    var selectedItem by remember { mutableStateOf(items.first()) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(items) { item ->
            val isSelected = (item == selectedItem)

            FilterChip(
                selected = (item == selectedItem),
                onClick = { selectedItem = item },
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
fun MesasGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        item {
            CardMesa(
                1,
                "Ciclano",
                10.0,
                35.10,
                "Rafael"
            )
        }
        item {
            CardMesa(2,
                "Fulano",
                10.00,
                10.00,
                "Rafael"
            )
        }
        item {
            CardMesa(3,
                "Beltrano",
                10.0,
                3.10,
                "Rafael"
            )
        }
    }
}

@Composable
fun CardMesa(numMesa: Int, nomeCliente: String, tempo: Double, preco: Double, waiter: String) {
    Card(
        modifier = Modifier
            .width(109.33.dp)
            .height(116.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red),
    ) {
        Box(Modifier.fillMaxSize()) {
            Column {
                Box {
                    Text(numMesa.toString(), fontSize = 24.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.account_circle))
                    Text(nomeCliente)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.schedule))
                    Text(tempo.toString())
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.paid))
                    Text(preco.toString())
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icone(imageVector = ImageVector.vectorResource(id = R.drawable.room_service))
                    Text(waiter)
                }
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MapaAtendimentoPreview() {
    Column {
        ParteCimaApp(onBackClick = {})
    }

}

@Preview
@Composable
private fun CardMesaPreview() {
    CardMesa(1, "Ciclano", 10.00, 35.10, "Rafael")
}