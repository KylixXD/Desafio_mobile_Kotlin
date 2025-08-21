package com.example.desafio_mesas_comandas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.desafio_mesas_comandas.ui.theme.Desafio_mesas_comandasTheme
import com.example.desafio_mesas_comandas.ui.theme.Typography
import com.example.desafio_mesas_comandas.ui.theme.neutro
import com.example.desafio_mesas_comandas.ui.theme.Typography
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Desafio_mesas_comandasTheme (dynamicColor = false){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier) {
                        TelaInicial()
                    }
                }
            }

        }
    }
}

@Composable
fun TelaInicial(modifier: Modifier = Modifier) {
    TopBar()
    TituloPage("Rafael", "Sei la Restaurante")
    MenuGrid()
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
fun Cards(texto: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = neutro
        ),
        onClick = {

        }
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
fun MenuGrid(modifier: Modifier = Modifier) {
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
                icon = ImageVector.vectorResource(id = R.drawable.add_icon)
            )
        }
        item {
            Cards(
                "Mapa de atendimento",
                icon = ImageVector.vectorResource(id = R.drawable.map_icon)
            )
        }
        item {
            Cards(
                "Configurações",
                icon = ImageVector.vectorResource(id = R.drawable.settings_icon)
            )
        }
    }
}

@Composable
fun NovoPedidoBottomShape(modifier: Modifier = Modifier) {
    var showBottomSheet by remember {  mutableStateOf(false) }

    Box(){

    }

}



