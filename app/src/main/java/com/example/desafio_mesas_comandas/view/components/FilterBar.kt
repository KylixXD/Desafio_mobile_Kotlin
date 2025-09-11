package com.example.desafio_mesas_comandas.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    selectedFilter: String,
    onFilterChange: (index: Int, filterName: String) -> Unit,
    listState: LazyListState
) {
    val filterList = listOf("Visão Geral", "Em Atendimento", "Ociosas", "Disponíveis", "Sem Pedidos")
    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        itemsIndexed(filterList) { index, item ->
            FilterChip(
                selected = (item == selectedFilter),
                onClick = { onFilterChange(index, item) },
                label = { Text(item) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White,
                    selectedContainerColor = Color.Black,
                    labelColor = Color.Black,
                    selectedLabelColor = Color.White,
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}
