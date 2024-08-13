package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DarkGreen
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.LightRed
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus
import com.wcsm.confectionaryadmin.ui.view.ordersMock

@Composable
fun ChangeStatusDialog(
    order: Order,
    onDissmiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var formattedStatus = ""
    var nextStatus = ""
    var statusColor = Color.Black
    var nextStatusColor = Color.Black

    when(order.status) {
        OrderStatus.QUOTATION -> {
            formattedStatus = "Orçamento"
            nextStatus = "Confirmado"
            statusColor = QuotationStatus
            nextStatusColor = ConfirmedStatus
        }
        OrderStatus.CONFIRMED -> {
            formattedStatus = "Confirmado"
            nextStatus = "Em Produção"
            statusColor = ConfirmedStatus
            nextStatusColor = InProductionStatus
        }
        OrderStatus.IN_PRODUCTION -> {
            formattedStatus = "Em Produção"
            nextStatus = "Finalizado"
            statusColor = InProductionStatus
            nextStatusColor = FinishedStatus
        }
        OrderStatus.FINISHED -> {
            formattedStatus = "Finalizado"
            nextStatus = "Entregue"
            statusColor = FinishedStatus
            nextStatusColor = DeliveredStatus
        }
        OrderStatus.DELIVERED -> {
            formattedStatus = "Entregue"
            statusColor = DeliveredStatus
        }
        OrderStatus.CANCELLED -> {
            formattedStatus = "Entregue"
            statusColor = DeliveredStatus
        }
    }
    
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(brush = AppBackground)
            .border(1.dp, Primary, RoundedCornerShape(15.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "Próximo Status",
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = "Você deseja enviar o pedido:",
            fontFamily = InterFontFamily,
            fontSize = 20.sp,
        )

        Text(
            text = order.title,
            color = Color.White,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "Para o status de:",
            fontFamily = InterFontFamily,
            fontSize = 20.sp,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = formattedStatus,
                color = statusColor,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
            )

            Text(
                text = nextStatus,
                color = nextStatusColor,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp),color = Color.White)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChangeStatusDialogButton(
                text = "NÃO",
                color = LightRed
            ) {
                onDissmiss()
            }

            ChangeStatusDialogButton(
                text = "SIM",
                color = DarkGreen
            ) {
                onConfirm()
            }
        }
    }
}

@Composable
private fun ChangeStatusDialogButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .width(100.dp)
            .border(1.dp, color, RoundedCornerShape(15.dp))
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = color,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun ChangeStatusDialogPreview() {
    ConfectionaryAdminTheme {
        val order = ordersMock[0]
        ChangeStatusDialog(
            order = order,
            onDissmiss = {},
            onConfirm = {}
        )
    }
}

@Preview
@Composable
private fun ChangeStatusDialogButtonPreview() {
    ConfectionaryAdminTheme {
        Column {
            ChangeStatusDialogButton(text = "SIM", color = DarkGreen) {}
            Spacer(modifier = Modifier.height(8.dp))
            ChangeStatusDialogButton(text = "NÃO", color = LightRed) {}
        }
    }
}