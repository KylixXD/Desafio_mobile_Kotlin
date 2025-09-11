@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.desafio_mesas_comandas.view

import NewOrderBottomSheet
import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.view.components.CardSheet
import com.example.desafio_mesas_comandas.view.components.CardsMenu
import com.example.desafio_mesas_comandas.view.components.HeaderHome
import com.example.desafio_mesas_comandas.view.components.TitleHome

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            HomePageSkeleton(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageSkeleton(navController: NavController, modifier: Modifier = Modifier) {
    var showBottomSheet by remember { mutableStateOf(false) }


    HeaderHome()
    TitleHome("Rafael Nóbrega", "Sei la Restaurante")
    MenuGrid(
        onNewOrderoClick = { showBottomSheet = true },
        onNavigate = { route -> navController.navigate(route) }
    )

    NewOrderBottomSheet(
        showSheet = showBottomSheet,
        onDismissRequest = { showBottomSheet = false }
    ) {
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Novo pedido",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Selecione o tipo de pedido",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        CardSheet(
            "Mesa/Comanda",
            rightIcon = ImageVector.vectorResource(id = R.drawable.chevron_right),
            leftIcon = ImageVector.vectorResource(id = R.drawable.table_restaurant),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        CardSheet(
            "Balcão",
            rightIcon = ImageVector.vectorResource(id = R.drawable.chevron_right),
            leftIcon = ImageVector.vectorResource(id = R.drawable.shopping_bag_speed),
            onClick = {}
        )
    }
}

@Composable
fun IconCustom(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @DrawableRes drawableRes: Int? = null,
    contentDescription: String? = null
) {
    when {
        imageVector != null -> {
            Icon(
                imageVector,
                contentDescription = contentDescription,
                modifier = modifier,
                tint = Color.Black
            )
        }

        drawableRes != null -> {
            Icon(
                painter = painterResource(id = drawableRes),
                contentDescription = contentDescription,
                modifier = modifier,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun MenuGrid(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    onNewOrderoClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        item {
            CardsMenu(
                "Novo Pedido",
                icon = ImageVector.vectorResource(id = R.drawable.add_icon),
                onClick = onNewOrderoClick,
            )
        }
        item {
            CardsMenu(
                "Mapa de atendimento",
                icon = ImageVector.vectorResource(id = R.drawable.map_icon),
                onClick = { onNavigate("MapScreen") },
            )
        }
        item {
            CardsMenu(
                "Configurações",
                icon = ImageVector.vectorResource(id = R.drawable.settings_icon),
                onClick = { },
            )
        }
    }
}



