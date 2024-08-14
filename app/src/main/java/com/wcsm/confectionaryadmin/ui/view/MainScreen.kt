package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import com.wcsm.confectionaryadmin.ui.components.CustomStatus
import com.wcsm.confectionaryadmin.ui.components.DateTimeContainer
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
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
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    ordersViewModel: OrdersViewModel
) {
    val order by ordersViewModel.ordersWithCustomer.collectAsState()

    var quotationOrders by rememberSaveable { mutableIntStateOf(0) }
    var confirmedOrders by rememberSaveable { mutableIntStateOf(0) }
    var inProductionOrders by rememberSaveable { mutableIntStateOf(0) }
    var finishedOrders by rememberSaveable { mutableIntStateOf(0) }
    var deliveredOrders by rememberSaveable { mutableIntStateOf(0) }
    var cancelledOrders by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(order) {
        quotationOrders = order.filter {
            it.order.status == OrderStatus.QUOTATION
        }.size

        confirmedOrders = order.filter {
            it.order.status == OrderStatus.CONFIRMED
        }.size

        inProductionOrders = order.filter {
            it.order.status == OrderStatus.IN_PRODUCTION
        }.size

        finishedOrders = order.filter {
            it.order.status == OrderStatus.FINISHED
        }.size

        deliveredOrders = order.filter {
            it.order.status == OrderStatus.DELIVERED
        }.size

        cancelledOrders = order.filter {
            it.order.status == OrderStatus.CANCELLED
        }.size
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(AppBackground)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.screen_title_main_screen),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                style = TextStyle(
                    brush = AppTitleGradient
                ),
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            DateTimeContainer()

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = stringResource(id = R.string.screen_title_orders_screen),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textDecoration = TextDecoration.Underline,
                style = TextStyle(
                    brush = AppTitleGradient
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = Primary)
                    .padding(24.dp)
            ) {
                CustomStatus(
                    text = stringResource(id = R.string.status_quotation),
                    color = QuotationStatus,
                    quantity = quotationOrders
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_confirmed),
                    color = ConfirmedStatus,
                    quantity = confirmedOrders
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_in_production),
                    color = InProductionStatus,
                    quantity = inProductionOrders
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_finished),
                    color = FinishedStatus,
                    quantity = finishedOrders
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_delivered),
                    color = DeliveredStatus,
                    quantity = deliveredOrders
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_cancelled),
                    color = CancelledStatus,
                    quantity = cancelledOrders
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview(ordersViewModel: OrdersViewModel = hiltViewModel()) {
    ConfectionaryAdminTheme {
        val paddingValues = PaddingValues()
        MainScreen(
            paddingValues = paddingValues,
            ordersViewModel = ordersViewModel
        )
    }
}