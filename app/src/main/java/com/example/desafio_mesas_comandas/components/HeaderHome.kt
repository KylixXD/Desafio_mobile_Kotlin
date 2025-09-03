package com.example.desafio_mesas_comandas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.desafio_mesas_comandas.R
import com.example.desafio_mesas_comandas.ui.theme.laranja

@Composable
fun HeaderHome(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(24.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Row(Modifier, verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(R.drawable.pigz_logo),
                contentDescription = null,
                modifier = modifier
                    .height(20.dp)
                    .width(39.73.dp),
            )
            Text("Comanda", color = laranja)
        }
    }
    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
}

@Preview
@Composable
private fun HeaderHomePreview() {
    HeaderHome()
}