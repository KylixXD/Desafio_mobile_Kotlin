package com.example.desafio_mesas_comandas.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.desafio_mesas_comandas.components.FilterBar
import com.example.desafio_mesas_comandas.components.SearchBarCustom
import com.example.desafio_mesas_comandas.components.TablesGrid
import com.example.desafio_mesas_comandas.components.TopBarCustom
import com.example.desafio_mesas_comandas.ui.theme.neutro
import com.example.desafio_mesas_comandas.viewmodel.MapViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            MapPage(onBackClick = { navController.popBackStack("HomePage", inclusive = false) })
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        MapPage(onBackClick = { navController.popBackStack() })
    }
}

@Composable
fun MapPage(
    onBackClick: () -> Unit,
    viewModel: MapViewModel = viewModel()
) {
    val lazyPagingItems = viewModel.tables.collectAsLazyPagingItems()
    val searchText by viewModel.searchText.collectAsState()
    val selectedFilter by viewModel.selectedFilterName.collectAsState()

    val filterListState = rememberLazyListState()
    val tableGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(neutro)
    ) {
        TopBarCustom("Mapa de atendimento", onBackClick)
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        Column(Modifier.background(Color.White)) {
            SearchBarCustom(
                value = searchText,
                onValueChange = viewModel::updateSearch
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }

        FilterBar(
            selectedFilter = selectedFilter,
            listState = filterListState,
            onFilterChange = { index, filterName ->
                viewModel.updateFilter(filterName)

                coroutineScope.launch {
                    filterListState.animateScrollToItem(index)
                    tableGridState.scrollToItem(0)
                }
            }
        )

        TablesGrid(
            lazyPagingItems = lazyPagingItems,
            gridState = tableGridState,
            Modifier.padding(bottom = 24.dp)
        )
    }
}

