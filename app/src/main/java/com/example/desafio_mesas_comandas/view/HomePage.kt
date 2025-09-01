@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.desafio_mesas_comandas.view

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.ui.theme.Typography
import com.example.desafio_mesas_comandas.ui.theme.neutro
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            TelaInicial(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInicial(navController: NavController, modifier: Modifier = Modifier) {
    var showBottomSheet by remember { mutableStateOf(false) }

    TopBar()
    TituloPage("Rafael", "Sei la Restaurante")
    MenuGrid(
        onNovoPedidoClick = { showBottomSheet = true },
        onNavigate = { route -> navController.navigate(route) }
    )

    NovoPedidoBottomSheet(
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

        CardsSheet(
            "Mesa/Comanda",
            iconeDireita = ImageVector.vectorResource(id = R.drawable.chevron_right),
            iconeEsquerda = ImageVector.vectorResource(id = R.drawable.table_restaurant),
            onClick = {
                scope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                    navController.navigate("MesaComanda")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CardsSheet(
            "Balcão",
            iconeDireita = ImageVector.vectorResource(id = R.drawable.chevron_right),
            iconeEsquerda = ImageVector.vectorResource(id = R.drawable.shopping_bag_speed),
            onClick = {
                scope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                    navController.navigate("balcaoComanda")
                }
            }
        )
    }
}

@Composable
fun Icone(
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
fun TituloPage(waiter: String, restaurante: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = waiter, style = Typography.titleLarge, modifier = Modifier)
        Text(
            text = restaurante,
            style = Typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier
        )
    }

}


@Composable
fun ImagemTopo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.logo_comanda),
        contentDescription = null,
        modifier = modifier.size(160.dp),
    )
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(24.dp))
    Column(
        modifier = Modifier
            .size(1000.dp, 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        ImagemTopo()
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Composable
fun Cards(texto: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = neutro
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icone(
                imageVector = icon,
                modifier = Modifier.size(32.dp),
                contentDescription = texto
            )
            Text(text = texto)
        }
    }
}

@Composable
fun CardsSheet(
    texto: String,
    iconeEsquerda: ImageVector,
    iconeDireita: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = neutro),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icone(
                imageVector = iconeEsquerda,
                modifier = Modifier.size(24.dp),
                contentDescription = texto
            )
            Text(
                texto,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )

            Icone(
                imageVector = iconeDireita,
                modifier = Modifier.size(24.dp),
                contentDescription = texto
            )
        }
    }
}

@Composable
fun MenuGrid(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    onNovoPedidoClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        item {

            Cards(
                "Novo Pedido",
                icon = ImageVector.vectorResource(id = R.drawable.add_icon),
                onClick = onNovoPedidoClick,
            )
        }
        item {
            Cards(
                "Mapa de atendimento",
                icon = ImageVector.vectorResource(id = R.drawable.map_icon),
                onClick = { onNavigate("mapaAtendimento") },
            )
        }
        item {
            Cards(
                "Configurações",
                icon = ImageVector.vectorResource(id = R.drawable.settings_icon),
                onClick = { onNavigate("mapaAtendimento") },
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NovoPedidoBottomSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    //val scope = rememberCoroutineScope()

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                content = content
            )
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    val navController = rememberNavController()
    HomePage(navController = navController)
}

