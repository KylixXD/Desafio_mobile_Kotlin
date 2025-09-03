package com.example.desafio_mesas_comandas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.desafio_mesas_comandas.ui.theme.Typography

@Composable
fun TitleHome(waiter: String, restaurant: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = waiter, style = Typography.titleLarge, modifier = Modifier)
        Text(
            text = restaurant,
            style = Typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier
        )
    }

}