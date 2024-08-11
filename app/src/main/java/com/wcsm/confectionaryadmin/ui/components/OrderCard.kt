package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import com.wcsm.confectionaryadmin.ui.theme.BrownColor
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus
import com.wcsm.confectionaryadmin.ui.theme.ValueColor
import com.wcsm.confectionaryadmin.ui.util.convertMillisToString
import com.wcsm.confectionaryadmin.ui.view.ordersMock

@Composable
fun OrderCard(
    order: Order,
    isExpanded: Boolean,
    onDelete: (order: Order) -> Unit,
    onExpandChange: (Boolean) -> Unit
) {
    var formattedStatus = ""
    var statusColor = Color.Black

    when(order.status) {
        OrderStatus.QUOTATION -> {
            formattedStatus = "Orçamento"
            statusColor = QuotationStatus
        }
        OrderStatus.CONFIRMED -> {
            formattedStatus = "Confirmado"
            statusColor = ConfirmedStatus
        }
        OrderStatus.IN_PRODUCTION -> {
            formattedStatus = "Em Produção"
            statusColor = InProductionStatus
        }
        OrderStatus.FINISHED -> {
            formattedStatus = "Finalizado"
            statusColor = FinishedStatus
        }
        OrderStatus.DELIVERED -> {
            formattedStatus = "Entregue"
            statusColor = DeliveredStatus
        }
        OrderStatus.CANCELLED -> {
            formattedStatus = "Cancelado"
            statusColor = CancelledStatus
        }
    }

    if(isExpanded) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(brush = InvertedAppBackground)
                .border(1.dp, Primary, RoundedCornerShape(15.dp))
                .width(328.dp)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.title,
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onExpandChange(false) }
                )
            }

            Divider(color = Color.White)

            Text(
                text = order.description,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Justify,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Divider(color = Color.White)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Valor: R$${order.price}",
                    color = ValueColor,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Status: $formattedStatus",
                    color = statusColor,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(color = Color.White)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = convertMillisToString(order.orderDate),
                    color = BrownColor,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(100.dp)
                )

                Text(
                    text = convertMillisToString(order.deliverDate),
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(100.dp)
                )

                Box {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = InProductionStatus,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .border(1.dp, Color.White, RoundedCornerShape(5.dp))
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = CancelledStatus,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.Black.copy(alpha = 0.8f))
                                .border(1.dp, Color.White, RoundedCornerShape(5.dp))
                                .clickable { onDelete(order) }
                        )
                    }
                }
            }

            Divider(color = Color.White, modifier = Modifier.padding(bottom = 8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .border(1.dp, Primary, RoundedCornerShape(15.dp))
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                        .clickable {
                            // NEXT STATUS
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.next_status_text),
                        color = Primary,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.next_custom_icon),
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(brush = InvertedAppBackground)
                .border(1.dp, Primary, RoundedCornerShape(15.dp))
                .width(328.dp)
                .padding(12.dp)
                .clickable { onExpandChange(true) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = order.title,
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(12.dp)
                    )

                    Text(
                        text = formattedStatus,
                        color = statusColor,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Text(
                    text = "Pedido: ${convertMillisToString(order.orderDate)}",
                    color = Color.White,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "Entrega: ${convertMillisToString(order.deliverDate)}",
                    color = Color.White,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                )

                Row() {
                    Text(
                        text = "Cliente:"
                    )

                    Text(
                        text = ""
                    )
                }

            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderCardPreview() {
    ConfectionaryAdminTheme {
        Column {

            ordersMock.forEach {
                OrderCard(
                    order = it,
                    isExpanded = false,
                    onDelete = {},
                    onExpandChange = {}
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}