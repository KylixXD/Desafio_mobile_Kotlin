package com.example.desafio_mesas_comandas.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.desafio_mesas_comandas.ui.theme.Typography

@Composable
fun TableInfoRow(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            Modifier.size(10.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(text, style = Typography.labelSmall, maxLines = 1)
    }
}