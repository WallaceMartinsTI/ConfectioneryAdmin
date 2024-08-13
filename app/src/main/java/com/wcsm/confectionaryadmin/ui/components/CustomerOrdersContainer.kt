package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.util.customersMock
import com.wcsm.confectionaryadmin.ui.view.ordersMock

@Composable
fun CustomerOrdersContainer(
    customer: Customer,
    orders: List<Order>,
    onDissmiss: () -> Unit
) {
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(AppBackground)
            .height(500.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.orders_from),
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Text(
                    text = customer.name,
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontSize = 24.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .clickable { onDissmiss() }
            )
        }

        Divider(
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(orders) {
                OrderCard(
                    order = it,
                    isExpanded = expandedStates[it.orderId] ?: false,
                    onEdit = {},
                    onDelete = {},
                    onChangeStatus = {}
                ) { expanded ->
                    expandedStates[it.orderId] = expanded
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun CustomerOrdersContainerPreview() {
    ConfectionaryAdminTheme {
        CustomerOrdersContainer(
            customer = customersMock[0],
            orders = ordersMock
        ) {}
    }
}