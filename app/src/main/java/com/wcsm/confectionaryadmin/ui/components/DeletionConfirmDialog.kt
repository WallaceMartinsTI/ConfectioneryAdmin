package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.ui.theme.BrownColor
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.LightRed
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.ValueColor
import com.wcsm.confectionaryadmin.ui.util.convertMillisToString
import com.wcsm.confectionaryadmin.ui.util.customersMock
import com.wcsm.confectionaryadmin.ui.util.getStatusColor
import com.wcsm.confectionaryadmin.ui.util.ordersMock
import com.wcsm.confectionaryadmin.ui.util.toBRL
import com.wcsm.confectionaryadmin.ui.util.toStatusString

@Composable
fun DeletionConfirmDialog(
    order: Order?,
    customerOwnerName: String? = null,
    customer: Customer?,
    onConfirm: () -> Unit,
    onDissmiss: () -> Unit,
) {
    if(order != null) {
        Dialog(onDismissRequest = { onDissmiss() }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(InvertedAppBackground)
                    .border(1.dp, Primary, RoundedCornerShape(15.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Tem certeza que deseja deletar o pedido abaixo:",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                    color = Color.White
                )

                Text(
                    text = "Pedido: ${order.title}",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                    , color = Color.White
                )

                Text(
                    text = "Descrição: ${order.description}",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                    , color = Color.White
                )

                Text(
                    text = "Preço: ${order.price.toBRL()}",
                    color = ValueColor,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "Status: ${order.status.toStatusString()}",
                    color = order.status.getStatusColor(),
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                    , color = Color.White
                )

                Text(
                    text = "Pedido: ${convertMillisToString(order.orderDate)}",
                    color = BrownColor,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Entrega: ${convertMillisToString(order.deliverDate)}",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                    , color = Color.White
                )

                if(customerOwnerName?.isNotEmpty() == true) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cliente: ",
                            color = Primary,
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = customerOwnerName,
                            color = Color.White,
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp)
                        , color = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomDialogButton(
                        text = "CONFIRMAR",
                        color = LightRed,
                        width = 200.dp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        onConfirm()
                    }

                    CustomDialogButton(
                        text = "CANCELAR",
                        color = Primary,
                        width = 200.dp
                    ) {
                        onDissmiss()
                    }
                }
            }
        }
    } else if(customer != null) {
        Dialog(onDismissRequest = { onDissmiss() }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(InvertedAppBackground)
                    .border(1.dp, Primary, RoundedCornerShape(15.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Tem certeza que deseja deletar o cliente abaixo:",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                    color = Color.White
                )

                Text(
                    text = "Nome: ${customer.name}",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Email: ${ customer.email ?: "" }",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Telefone: ${ customer.phone ?: "" }",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Gênero: ${ customer.gender ?: "" }",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                    , color = Color.White
                )

                Text(
                    text = "PEDIDOS: 08",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "CLIENTE DESDE: 12/03/2024",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                    , color = Color.White
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomDialogButton(
                        text = "CONFIRMAR",
                        color = LightRed,
                        width = 200.dp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        onConfirm()
                    }

                    CustomDialogButton(
                        text = "CANCELAR",
                        color = Primary,
                        width = 200.dp
                    ) {
                        onDissmiss()
                    }
                }
            }
        }
    }
}

@Preview(name = "Delete Order")
@Composable
private fun DeletionConfirmDialogOrderPreview() {
    ConfectionaryAdminTheme {
        DeletionConfirmDialog(
            order = ordersMock[0],
            customerOwnerName = "João",
            customer = null,
            onConfirm = {},
            onDissmiss = {}
        )
    }
}

@Preview(name = "Delete Customer")
@Composable
private fun DeletionConfirmDialogCustomerPreview() {
    ConfectionaryAdminTheme {
        DeletionConfirmDialog(
            order = null,
            customer = customersMock[0],
            onConfirm = {},
            onDissmiss = {}
        )
    }
}