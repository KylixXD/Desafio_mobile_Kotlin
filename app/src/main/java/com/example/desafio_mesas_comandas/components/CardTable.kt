package com.example.desafio_mesas_comandas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.desafio_mesas_comandas.data.local.TableEntity
import com.example.desafio_mesas_comandas.ui.theme.Typography
import com.example.desafio_mesas_comandas.ui.theme.amarelo
import com.example.desafio_mesas_comandas.ui.theme.verde
import com.example.desafio_mesas_comandas.ui.theme.vermelho
import com.example.desafio_mesas_comandas.utils.maskForIdleTime
import com.example.desafio_mesas_comandas.utils.toBrazilianCurrencyFromCents

@Composable
fun CardTable(
    mesa: TableEntity,
    receiptIcon: ImageVector,
    accountCircleIcon: ImageVector,
    scheduleIcon: ImageVector,
    paidIcon: ImageVector,
    roomServiceIcon: ImageVector
) {
    val backgroundColors = remember(mesa.activity) {
        when (mesa.activity) {
            "inactive" -> vermelho
            "active" -> verde
            "waiting" -> amarelo
            else -> Color.White
        }
    }

    val hasContent = mesa.activity != "empty"

    Card(
        modifier = Modifier
            .width(110.dp)
            .height(116.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColors),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = "${mesa.title}",
                style = Typography.titleLarge,
                modifier = Modifier.padding(start = 4.dp)
            )

            if (hasContent) {
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (mesa.orderCount > 0) {
                        Row(
                            Modifier.padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                receiptIcon,
                                contentDescription = "Pedidos",
                                Modifier.size(10.dp)
                            )
                            Text(text = "${mesa.orderCount}", style = Typography.labelSmall)
                        }

                    }

                    Icon(
                        accountCircleIcon,
                        contentDescription = "Clientes",
                        Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    val customerInfoText =
                        if (mesa.numberCustomer == 1 && !mesa.customerName.isNullOrBlank()) {
                            mesa.customerName
                        } else {
                            "${mesa.numberCustomer ?: 0}"
                        }
                    Text(text = customerInfoText, style = Typography.labelSmall, maxLines = 1)
                }

                TableInfoRow(
                    scheduleIcon,
                    text = mesa.idleTime.maskForIdleTime()
                )
                TableInfoRow(
                    paidIcon,
                    text = mesa.subTotal?.toBrazilianCurrencyFromCents() ?: "R$ 0,00"
                )
                val sellerText = when {
                    !mesa.customerName.isNullOrBlank() -> mesa.customerName
                    !mesa.sellerName.isNullOrBlank() -> mesa.sellerName
                    else -> "Não informado"
                }
                TableInfoRow(
                    roomServiceIcon,
                    text = sellerText
                )
            }
        }
    }
}

