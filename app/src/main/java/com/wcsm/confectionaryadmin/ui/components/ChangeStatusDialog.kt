package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
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
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.DarkGreen
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.LightRed
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.util.getNextStatus
import com.wcsm.confectionaryadmin.ui.util.getStatusColor
import com.wcsm.confectionaryadmin.ui.util.toStatusString
import com.wcsm.confectionaryadmin.ui.view.ordersMock

@Composable
fun ChangeStatusDialog(
    order: Order,
    onDissmiss: () -> Unit,
    onConfirm: () -> Unit
) {
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
                text = order.status.toStatusString(),
                color = order.status.getStatusColor(),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
            )

            Text(
                text = order.status.getNextStatus().toStatusString(),
                color = order.status.getNextStatus().getStatusColor(),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.White)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomDialogButton(
                text = "NÃO",
                color = LightRed,
                width = 100.dp
            ) {
                onDissmiss()
            }

            CustomDialogButton(
                text = "SIM",
                color = DarkGreen,
                width = 100.dp
            ) {
                onConfirm()
            }
        }
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