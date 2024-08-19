package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.CustomTimePicker
import com.wcsm.confectionaryadmin.ui.components.CustomTopAppBar
import com.wcsm.confectionaryadmin.ui.components.MinimizedCustomerCard
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurple
import com.wcsm.confectionaryadmin.ui.util.CurrencyVisualTransformation
import com.wcsm.confectionaryadmin.ui.util.convertMillisToString
import com.wcsm.confectionaryadmin.ui.util.getStringStatusFromStatus
import com.wcsm.confectionaryadmin.ui.util.showToastMessage
import com.wcsm.confectionaryadmin.ui.util.toBrazillianDateFormat
import com.wcsm.confectionaryadmin.ui.util.toOrderStatus
import com.wcsm.confectionaryadmin.ui.viewmodel.CreateOrderViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrderScreen(
    navController: NavController,
    ordersViewModel: OrdersViewModel,
    customersViewModel: CustomersViewModel,
    createOrderViewModel: CreateOrderViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val orderState by createOrderViewModel.orderState.collectAsState()
    val newOrderCreated by createOrderViewModel.newOrderCreated.collectAsState()
    val orderUpdated by createOrderViewModel.orderUpdated.collectAsState()

    val customers by customersViewModel.customers.collectAsState()

    val orderToBeEditted by ordersViewModel.orderToBeEditted.collectAsState()

    var value by rememberSaveable { mutableStateOf("0") }

    var stringStatus by rememberSaveable {
        mutableStateOf(getStringStatusFromStatus(orderState.status))
    }

    var statusDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    var orderDateShowDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val orderDateDatePickerState = rememberDatePickerState()
    var orderDateSelectedDate by rememberSaveable { mutableStateOf("") }

    var orderDateShowTimePickerDialog by rememberSaveable { mutableStateOf(false) }
    var orderDateSelectedTime by rememberSaveable { mutableStateOf("") }

    var deliverDateShowDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val deliverDateDatePickerState = rememberDatePickerState()
    var deliverDateSelectedDate by rememberSaveable { mutableStateOf("") }

    var deliverDateShowTimePickerDialog by rememberSaveable { mutableStateOf(false) }
    var deliverDateSelectedTime by rememberSaveable { mutableStateOf("") }

    var showCustomerChooser by rememberSaveable { mutableStateOf(false) }

    val focusRequester = rememberSaveable { List(3) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    val customBlur = if (showCustomerChooser) 8.dp else 0.dp

    DisposableEffect(Unit) {
        onDispose {
            ordersViewModel.updateOrderToBeEditted(null)
        }
    }

    LaunchedEffect(newOrderCreated) {
        if(newOrderCreated) {
            ordersViewModel.getAllOrders()
            navController.navigate(Screen.Orders.route)
        }
    }

    LaunchedEffect(orderUpdated) {
        if(orderUpdated) {
            createOrderViewModel.updateOrderUpdated(false)
            ordersViewModel.getAllOrders()
            showToastMessage(context, "Pedido atualizado.")
        }
    }

    LaunchedEffect(orderToBeEditted) {
        if(orderToBeEditted != null) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderId = orderToBeEditted!!.order.orderId,
                    customer = orderToBeEditted!!.customer,
                    orderName = orderToBeEditted!!.order.title,
                    orderDescription = orderToBeEditted!!.order.description,
                    orderDate = convertMillisToString(orderToBeEditted!!.order.orderDate),
                    deliverDate = convertMillisToString(orderToBeEditted!!.order.deliverDate),
                    status = orderToBeEditted!!.order.status
                )
            )
            val orderPrice = orderToBeEditted!!.order.price == 0.0
            value = if(orderPrice) "0" else orderToBeEditted!!.order.price.toString()
        }
    }

    LaunchedEffect(orderState.status) {
        stringStatus = getStringStatusFromStatus(orderState.status)
    }

    LaunchedEffect(orderDateSelectedDate, orderDateSelectedTime) {
        if (orderDateSelectedDate.isNotEmpty() && orderDateSelectedTime.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderDate = "$orderDateSelectedDate $orderDateSelectedTime"
                )
            )
        }
    }

    LaunchedEffect(deliverDateSelectedDate, deliverDateSelectedTime) {
        if (deliverDateSelectedDate.isNotEmpty() && deliverDateSelectedTime.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    deliverDate = "$deliverDateSelectedDate $deliverDateSelectedTime"
                )
            )
        }
    }

    LaunchedEffect(orderState.customer) {
        if(orderState.customer != null) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    customerErrorMessage = null
                )
            )
        }
    }

    LaunchedEffect(orderState.orderName) {
        if(orderState.orderName.length >= 3) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderNameErrorMessage = null
                )
            )
        }
    }

    LaunchedEffect(orderState.orderDescription) {
        if(orderState.orderDescription.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderDescriptionErrorMessage = null
                )
            )
        }
    }

    LaunchedEffect(orderState.orderDate) {
        if(orderState.orderDate.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderDateErrorMessage = null
                )
            )
        }
    }

    LaunchedEffect(orderState.deliverDate) {
        if(orderState.deliverDate.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    deliverDateErrorMessage = null
                )
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopAppBar(
            title = stringResource(id = R.string.screen_title_create_order_screen),
        ) {
            navController.popBackStack()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(customBlur)
                .background(AppBackground),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenDescription(
                    description = stringResource(id = R.string.create_order_screen_description),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.width(280.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            CustomTextField(
                                label = stringResource(id = R.string.textfield_label_customer),
                                placeholder = "Selecione ao lado >",
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                width = 200.dp,
                                readOnly = true,
                                errorMessage = orderState.customerErrorMessage,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null
                                    )
                                },
                                value = orderState.customer?.name ?: ""
                            ) { }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        ChooseCustomerForOrderButton {
                            showCustomerChooser = true
                        }
                    }

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_order_name),
                        placeholder = stringResource(id = R.string.textfield_placeholder_order_name),
                        modifier = Modifier.focusRequester(focusRequester[0]),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        errorMessage = orderState.orderNameErrorMessage,
                        charactereLimit = 40,
                        charactereCounter = orderState.orderName.isNotEmpty(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Notes,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if(orderState.orderName.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        createOrderViewModel.updateCreateOrderState(
                                            orderState.copy(
                                                orderName = ""
                                            )
                                        )
                                        focusRequester[0].requestFocus()
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                        },
                        value = orderState.orderName
                    ) {
                        createOrderViewModel.updateCreateOrderState(
                            orderState.copy(
                                orderName = it
                            )
                        )
                    }

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_valor),
                        placeholder = "",
                        modifier = Modifier.focusRequester(focusRequester[2]),
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next,
                        singleLine = true,
                        errorMessage = orderState.priceErrorMessage,
                        visualTransformation = CurrencyVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if(value != "0") {
                                if (value != "") {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            value = "0"
                                            focusRequester[2].requestFocus()
                                        }
                                    )
                                }
                            }
                        },
                        value = value
                    ) { newValue ->
                        if(newValue.all { it.isDigit() }) {
                            value = newValue.ifEmpty {
                                ""
                            }
                        }
                    }

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_description),
                        placeholder = stringResource(id = R.string.textfield_placeholder_description),
                        modifier = Modifier.focusRequester(focusRequester[1]),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        singleLine = false,
                        maxLines = 8,
                        charactereLimit = 100,
                        charactereCounter = orderState.orderDescription.isNotEmpty(),
                        errorMessage = orderState.orderDescriptionErrorMessage,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Note,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if(orderState.orderDescription.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        createOrderViewModel.updateCreateOrderState(
                                            orderState.copy(
                                                orderDescription = ""
                                            )
                                        )
                                        focusRequester[1].requestFocus()
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                        },
                        value = orderState.orderDescription
                    ) {
                        createOrderViewModel.updateCreateOrderState(
                            orderState.copy(
                                orderDescription = it
                            )
                        )
                    }

                    if (orderDateShowDatePickerDialog) {
                        DatePickerDialog(
                            onDismissRequest = {
                                orderDateShowDatePickerDialog = false
                                focusManager.clearFocus()
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        orderDateDatePickerState.selectedDateMillis?.let { millis ->
                                            orderDateSelectedDate = millis.toBrazillianDateFormat()
                                        }
                                        orderDateShowDatePickerDialog = false
                                        orderDateShowTimePickerDialog = true
                                        focusManager.clearFocus()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Primary
                                    )
                                ) {
                                    Text(text = stringResource(id = R.string.choose_date))
                                }
                            }
                        ) {
                            DatePicker(
                                state = orderDateDatePickerState,
                                showModeToggle = false,
                                colors = DatePickerDefaults.colors(
                                    headlineContentColor = Primary,
                                    weekdayContentColor = Primary,
                                    currentYearContentColor = Primary,
                                    selectedYearContainerColor = Primary,
                                    selectedDayContainerColor = Primary,
                                    todayContentColor = Primary,
                                    todayDateBorderColor = Primary
                                )
                            )
                        }
                    }

                    if(orderDateShowTimePickerDialog) {
                        Dialog(
                            onDismissRequest = { orderDateShowTimePickerDialog = false },
                        ) {
                            CustomTimePicker(
                                onDismiss = { orderDateShowTimePickerDialog = false }
                            ) { time ->
                                orderDateSelectedTime = time
                                orderDateShowTimePickerDialog = false
                            }
                        }
                    }

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_order_request_date),
                        placeholder = "",
                        modifier = Modifier
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    orderDateShowDatePickerDialog = true
                                }
                            },
                        errorMessage = orderState.orderDateErrorMessage,
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        value = orderState.orderDate
                    ) { }

                    if (deliverDateShowDatePickerDialog) {
                        DatePickerDialog(
                            onDismissRequest = {
                                deliverDateShowDatePickerDialog = false
                                focusManager.clearFocus()
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        deliverDateDatePickerState.selectedDateMillis?.let { millis ->
                                            deliverDateSelectedDate = millis.toBrazillianDateFormat()
                                        }
                                        deliverDateShowDatePickerDialog = false
                                        deliverDateShowTimePickerDialog = true
                                        focusManager.clearFocus()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Primary
                                    )
                                ) {
                                    Text(text = stringResource(id = R.string.choose_date))
                                }
                            }
                        ) {
                            DatePicker(
                                state = deliverDateDatePickerState,
                                showModeToggle = false,
                                colors = DatePickerDefaults.colors(
                                    headlineContentColor = Primary,
                                    weekdayContentColor = Primary,
                                    currentYearContentColor = Primary,
                                    selectedYearContainerColor = Primary,
                                    selectedDayContainerColor = Primary,
                                    todayContentColor = Primary,
                                    todayDateBorderColor = Primary
                                )
                            )
                        }
                    }

                    if(deliverDateShowTimePickerDialog) {
                        Dialog(
                            onDismissRequest = { deliverDateShowTimePickerDialog = false },
                        ) {
                            CustomTimePicker(
                                onDismiss = { deliverDateShowTimePickerDialog = false }
                            ) { time ->
                                deliverDateSelectedTime = time
                                deliverDateShowTimePickerDialog = false
                            }
                        }
                    }

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_order_deliver_date),
                        placeholder = "",
                        modifier = Modifier
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    deliverDateShowDatePickerDialog = true
                                }
                            },
                        errorMessage = orderState.deliverDateErrorMessage,
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        value = orderState.deliverDate
                    ) { }

                    Box {
                        ExposedDropdownMenuBox(
                            expanded = statusDropdownExpanded,
                            onExpandedChange = {
                                statusDropdownExpanded = !statusDropdownExpanded
                            }
                        ) {
                            CustomTextField(
                                modifier = Modifier.menuAnchor(),
                                label = stringResource(id = R.string.textfield_label_status),
                                placeholder = "",
                                errorMessage = null,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector =
                                        if (statusDropdownExpanded) Icons.Filled.KeyboardArrowUp
                                        else Icons.Filled.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Primary
                                    )
                                },
                                singleLine = true,
                                readOnly = true,
                                value = stringStatus
                            ) {
                                statusDropdownExpanded = !statusDropdownExpanded
                            }

                            ExposedDropdownMenu(
                                expanded = statusDropdownExpanded,
                                onDismissRequest = { statusDropdownExpanded = false },
                                modifier = Modifier.background(color = StrongDarkPurple)
                            ) {
                                val genderOptions = listOf(
                                    stringResource(id = R.string.status_quotation),
                                    stringResource(id = R.string.status_confirmed),
                                    stringResource(id = R.string.status_in_production),
                                    stringResource(id = R.string.status_finished),
                                    stringResource(id = R.string.status_delivered),
                                    stringResource(id = R.string.status_cancelled)
                                )

                                genderOptions.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = it,
                                                color = Color.White,
                                                fontFamily = InterFontFamily,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        },
                                        onClick = {
                                            createOrderViewModel.updateCreateOrderState(
                                                orderState.copy(
                                                    status = it.toOrderStatus()
                                                )
                                            )
                                            statusDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if(orderToBeEditted != null) {
                        PrimaryButton(text = stringResource(id = R.string.btn_text_save_edit)) {
                            if(value.isEmpty()) value = "0"
                            createOrderViewModel.updateCreateOrderState(
                                orderState.copy(
                                    price = value
                                )
                            )
                            createOrderViewModel.createNewOrder(isUpdateOrder = true)
                        }
                    } else {
                        PrimaryButton(text = stringResource(id = R.string.btn_text_create_order)) {
                            if(value.isEmpty()) value = "0"
                            createOrderViewModel.updateCreateOrderState(
                                orderState.copy(
                                    price = value
                                )
                            )
                            createOrderViewModel.createNewOrder()
                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            if (showCustomerChooser) {
                ChooseCustomerForOrder(
                    customers = customers,
                    onDissmiss = { showCustomerChooser = false }
                ) {
                    createOrderViewModel.updateCreateOrderState(
                        orderState.copy(
                            customer = it
                        )
                    )
                    showCustomerChooser = false
                }
            }
        }
    }
}

@Composable
private fun ChooseCustomerForOrderButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .width(60.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(ButtonBackground)
            .border(
                width = 1.dp,
                color = Primary,
                shape = RoundedCornerShape(15.dp)
            )
            .height(60.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.PersonAddAlt,
            contentDescription = null
        )
    }
}

@Composable
fun ChooseCustomerForOrder(
    customers: List<Customer>,
    onDissmiss: () -> Unit,
    onClick: (customer: Customer) -> Unit
) {
    Dialog(onDismissRequest = { onDissmiss() }) {
        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(brush = AppBackground)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(customers) {
                MinimizedCustomerCard(
                    customer = it,
                ) {
                    onClick(it)
                }
            }
        }
    }
}

/*
@Preview
@Composable
private fun CreateOrderScreenPreview(customersViewModel: CustomersViewModel = hiltViewModel()) {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        CreateOrderScreen(
            navController = navController,
            customersViewModel = customersViewModel
        )
    }
}

@Preview
@Composable
private fun ChooseCustomerForOrderButtonPreview() {
    ConfectionaryAdminTheme {
        ChooseCustomerForOrderButton {}
    }
}*/
