package com.example.desafio_mesas_comandas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.ui.theme.laranja

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCustom(title: String, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back_black_24dp_1),
                        contentDescription = title,
                        tint = laranja
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )
    }
}
