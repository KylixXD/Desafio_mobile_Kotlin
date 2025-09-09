package com.example.desafio_mesas_comandas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.desafio_mesas_comandas.data.local.TableEntity

@Composable
fun TablesGrid(
    lazyPagingItems: LazyPagingItems<TableEntity>,
    gridState: LazyGridState,
    modifier: Modifier = Modifier
) {
    val receiptIcon = Icons.Default.Receipt
    val accountCircleIcon = Icons.Default.AccountCircle
    val scheduleIcon = Icons.Default.Schedule
    val paidIcon = Icons.Default.Paid
    val roomServiceIcon = Icons.Default.RoomService

    Box(modifier = modifier.fillMaxSize()) {
        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Falha ao carregar os dados.\nVerifique sua conexão e tente novamente.",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 34.dp)

            ) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = { index -> lazyPagingItems[index]?.id ?: index }
                ) { index ->
                    lazyPagingItems[index]?.let { mesa ->
                        CardTable(
                            mesa = mesa,
                            receiptIcon = receiptIcon,
                            accountCircleIcon = accountCircleIcon,
                            scheduleIcon = scheduleIcon,
                            paidIcon = paidIcon,
                            roomServiceIcon = roomServiceIcon
                        )
                    }
                }

                lazyPagingItems.loadState.apply {
                    when {
                        append is LoadState.Loading -> {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Loading()
                                }
                            }
                        }

                        append is LoadState.Error -> {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
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

        if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Loading()
            }
        }
    }
}