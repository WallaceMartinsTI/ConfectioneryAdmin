package com.wcsm.confectionaryadmin.ui.view

import android.util.Log
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus
import com.wcsm.confectionaryadmin.ui.components.CustomLoading
import com.wcsm.confectionaryadmin.ui.components.CustomStatus
import com.wcsm.confectionaryadmin.ui.components.DateTimeContainer
import com.wcsm.confectionaryadmin.ui.components.SyncDialog
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DarkGreen
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus
import com.wcsm.confectionaryadmin.ui.util.showToastMessage
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.LoginViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    ordersViewModel: OrdersViewModel,
    customersViewModel: CustomersViewModel,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current

    val orders by ordersViewModel.ordersWithCustomer.collectAsState()

    val isConnected by loginViewModel.isConnected.collectAsState()

    val orderSyncState by ordersViewModel.orderSyncState.collectAsState()
    val customerSyncState by customersViewModel.customerSyncState.collectAsState()

    val confirmSyncUpDialogPreference by loginViewModel.showConfirmSyncUpDialog.collectAsState()

    var isSincronized by rememberSaveable { mutableStateOf(false) }

    var quotationOrders by rememberSaveable { mutableIntStateOf(0) }
    var confirmedOrders by rememberSaveable { mutableIntStateOf(0) }
    var inProductionOrders by rememberSaveable { mutableIntStateOf(0) }
    var finishedOrders by rememberSaveable { mutableIntStateOf(0) }
    var deliveredOrders by rememberSaveable { mutableIntStateOf(0) }
    var cancelledOrders by rememberSaveable { mutableIntStateOf(0) }

    var isScreenLoading by rememberSaveable { mutableStateOf(true) }
    var isSyncLoading by rememberSaveable { mutableStateOf(false) }
    var showSyncMessage by remember { mutableStateOf(false) }

    var showSyncUpConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        loginViewModel.checkConnection()
    }

    LaunchedEffect(confirmSyncUpDialogPreference) {
        loginViewModel.checkShowSyncUpConfirmDialog()
    }

    LaunchedEffect(orders) {
        quotationOrders = orders.filter {
            it.order.status == OrderStatus.QUOTATION
        }.size

        confirmedOrders = orders.filter {
            it.order.status == OrderStatus.CONFIRMED
        }.size

        inProductionOrders = orders.filter {
            it.order.status == OrderStatus.IN_PRODUCTION
        }.size

        finishedOrders = orders.filter {
            it.order.status == OrderStatus.FINISHED
        }.size

        deliveredOrders = orders.filter {
            it.order.status == OrderStatus.DELIVERED
        }.size

        cancelledOrders = orders.filter {
            it.order.status == OrderStatus.CANCELLED
        }.size

        isScreenLoading = false
    }

    LaunchedEffect(orderSyncState, customerSyncState) {
        if(orderSyncState.isSincronized && customerSyncState.isSincronized) {
            if(showSyncMessage) {
                showToastMessage(context, "Dados Sincronizados com Sucesso!")
                showSyncMessage = false
            }
            isSincronized = true
            Log.i("#-# SYNC #-#", "Data sent from Room to Firestore successfully")
            isSyncLoading = false
        }

        if(orderSyncState.syncError || customerSyncState.syncError) {
            showToastMessage(context, "Erro ao sincronizar, contate o administrador.")
            isSyncLoading = false
            showSyncMessage = false
        }
    }

    if(isScreenLoading) {
        Column(
            modifier = Modifier
                .background(AppBackground)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomLoading(size = 80.dp)
        }
    } else {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .background(AppBackground)
                    .fillMaxSize()
                    .padding(paddingValues),
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
                    modifier = Modifier.padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                DateTimeContainer()

                Spacer(modifier = Modifier.height(20.dp))

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

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .width(250.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(15.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(isSyncLoading) {
                        Column(
                            modifier = Modifier.size(180.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Sincronizando",
                                color = Primary,
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            CustomLoading(size = 80.dp)
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(0.dp))
                            val text = if(isSincronized) "ATUALIZADO" else "DESATUALIZADO"
                            val color = if(isSincronized) DarkGreen else InProductionStatus
                            Text(
                                text = text,
                                color = color,
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )

                            if(isSincronized) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = color
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.NotificationImportant,
                                    contentDescription = null,
                                    tint = color
                                )
                            }
                        }

                        Text(
                            text = "Sempre mantenha o app atualizado, para que os dados fiquem salvos na nuvem.",
                            color = Primary,
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .clickable {
                                    loginViewModel.checkConnection()

                                    if (!isSincronized) {
                                        loginViewModel.checkShowSyncUpConfirmDialog()

                                        if (confirmSyncUpDialogPreference != true) {
                                            showSyncUpConfirmDialog = true
                                        } else {
                                            if (isConnected) {
                                                showSyncMessage = true
                                                isSyncLoading = true
                                                loginViewModel.checkConnection()
                                                ordersViewModel.sendOrdersToSincronize()
                                                customersViewModel.sendCustomersToSincronize()
                                            } else {
                                                showToastMessage(context, "Sem conexão no momento")
                                            }
                                        }
                                    }
                                }
                                .border(1.dp, Primary, RoundedCornerShape(15.dp))
                                .background(brush = AppTitleGradient)
                                .width(290.dp)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val text = if(isSincronized) "ATUALIZADO" else "SINCRONIZAR"
                            Text(
                                text = text,
                                color = Color.White,
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if(!isSincronized) {
                                Icon(
                                    imageVector = Icons.Default.CloudSync,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

            if(showSyncUpConfirmDialog) {
                SyncDialog(
                    isSendingFromRoomToFirestore = true,
                    onDontShowAgain = {
                        loginViewModel.changeSyncUpConfirmDialogPreference(it)
                    },
                    onDissmiss = { showSyncUpConfirmDialog = false }
                ) {
                    loginViewModel.checkConnection()
                    if (isConnected) {
                        showSyncMessage = true
                        isSyncLoading = true
                        loginViewModel.checkConnection()
                        ordersViewModel.sendOrdersToSincronize()
                        customersViewModel.sendCustomersToSincronize()
                    } else {
                        showToastMessage(context, "Sem conexão no momento")
                    }
                }
            }
        }
    }
}

/*
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
}*/
