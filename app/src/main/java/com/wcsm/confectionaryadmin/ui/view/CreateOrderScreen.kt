package com.wcsm.confectionaryadmin.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Notes
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.CustomTopAppBar
import com.wcsm.confectionaryadmin.ui.components.MinimizedCustomerCard
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurple
import com.wcsm.confectionaryadmin.ui.util.getStatusFromString
import com.wcsm.confectionaryadmin.ui.util.getStringStatusFromStatus
import com.wcsm.confectionaryadmin.ui.util.toBrazillianDateFormat
import com.wcsm.confectionaryadmin.ui.viewmodel.CreateOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrderScreen(
    navController: NavController,
    createOrderViewModel: CreateOrderViewModel = hiltViewModel()
) {
    val orderState by createOrderViewModel.orderState.collectAsState()
    val newOrderCreated by createOrderViewModel.newOrderCreated.collectAsState()

    var stringStatus by remember {
        mutableStateOf(getStringStatusFromStatus(orderState.status))
    }

    var statusDropdownExpanded by remember { mutableStateOf(false) }

    var orderDateShowDatePickerDialog by remember { mutableStateOf(false) }
    val orderDateDatePickerState = rememberDatePickerState()
    var orderDateSelectedDate by remember { mutableStateOf("") }

    var deliverDateShowDatePickerDialog by remember { mutableStateOf(false) }
    val deliverDateDatePickerState = rememberDatePickerState()
    var deliverDateSelectedDate by remember { mutableStateOf("") }

    var showCustomerChooser by remember { mutableStateOf(false) }

    val focusRequester = remember { List(2) { FocusRequester() } }

    val customBlur = if (showCustomerChooser) 8.dp else 0.dp

    LaunchedEffect(orderDateSelectedDate) {
        if (orderDateSelectedDate.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderDate = orderDateSelectedDate
                )
            )
        }
    }

    LaunchedEffect(deliverDateSelectedDate) {
        if (deliverDateSelectedDate.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    deliverDate = deliverDateSelectedDate
                )
            )
        }
    }

    LaunchedEffect(orderState.status) {
        stringStatus = getStringStatusFromStatus(orderState.status)
    }

    LaunchedEffect(newOrderCreated) {
        if(newOrderCreated) {
            navController.navigate(Screen.Orders.route)
        }
    }

    LaunchedEffect(
        orderState.customer,
        orderState.orderName,
        orderState.orderDescription
    ) {
        if(orderState.customer != null) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    customerErrorMessage = null
                )
            )
        } else if(orderState.orderName.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderNameErrorMessage = null
                )
            )
        } else if(orderState.orderDescription.isNotEmpty()) {
            createOrderViewModel.updateCreateOrderState(
                orderState.copy(
                    orderDescriptionErrorMessage = null
                )
            )
        }
    }

    // SCAFFOLD dentro de outro SCAFFOLD (Da Bottom Navigation)
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CustomTopAppBar(
            title = stringResource(id = R.string.screen_title_create_order_screen),
        ) {
            navController.popBackStack()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .blur(customBlur)
                .background(AppBackground)
                .border(1.dp, Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.Blue),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ScreenDescription(
                    description = stringResource(id = R.string.create_order_screen_description),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        //.fillMaxWidth()
                        //.fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .border(1.dp, Color.Yellow),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.width(280.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            CustomTextField(
                                label = stringResource(id = R.string.textfield_label_customer),
                                placeholder = "Selecione ao lado >",
                                modifier = Modifier
                                    .focusRequester(focusRequester[0]),
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
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        errorMessage = orderState.orderNameErrorMessage,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Notes,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
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
                        label = stringResource(id = R.string.textfield_label_description),
                        placeholder = stringResource(id = R.string.textfield_placeholder_description),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        singleLine = false,
                        maxLines = 8,
                        errorMessage = orderState.orderDescriptionErrorMessage,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Note,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        value = orderState.orderDescription
                    ) {
                        createOrderViewModel.updateCreateOrderState(
                            orderState.copy(
                                orderDescription = it
                            )
                        )
                    }

                    // PRICE

                    if (orderDateShowDatePickerDialog) {
                        DatePickerDialog(
                            onDismissRequest = {
                                orderDateShowDatePickerDialog = false
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        orderDateDatePickerState.selectedDateMillis?.let { millis ->
                                            orderDateSelectedDate = millis.toBrazillianDateFormat()
                                        }
                                        orderDateShowDatePickerDialog = false
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

                    Spacer(modifier = Modifier.height(120.dp))

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_order_request_date),
                        placeholder = "",
                        modifier = Modifier
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    orderDateShowDatePickerDialog = true
                                }
                            },
                        errorMessage = null,
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
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        deliverDateDatePickerState.selectedDateMillis?.let { millis ->
                                            deliverDateSelectedDate = millis.toBrazillianDateFormat()
                                        }
                                        deliverDateShowDatePickerDialog = false
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

                    Spacer(modifier = Modifier.height(120.dp))

                    CustomTextField(
                        label = stringResource(id = R.string.textfield_label_order_deliver_date),
                        placeholder = "",
                        modifier = Modifier
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    deliverDateShowDatePickerDialog = true
                                }
                            },
                        errorMessage = null,
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

                    Spacer(modifier = Modifier.height(120.dp))

                    Box {
                        ExposedDropdownMenuBox(
                            expanded = statusDropdownExpanded,
                            onExpandedChange = {
                                statusDropdownExpanded = !statusDropdownExpanded
                            }
                        ) {
                            CustomTextField(
                                modifier = Modifier.menuAnchor(),
                                label = stringResource(id = R.string.textfield_label_gender),
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
                                                    status = getStatusFromString(it)
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

                    PrimaryButton(text = stringResource(id = R.string.btn_text_create_order)) {
                        createOrderViewModel.createNewOrder()
                    }
                }
            }

            if (showCustomerChooser) {
                ChooseCustomerForOrder(
                    customers = customersMock,
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

@Preview
@Composable
private fun CreateOrderScreenPreview() {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        CreateOrderScreen(navController = navController)
    }
}

@Preview
@Composable
private fun ChooseCustomerForOrderButtonPreview() {
    ConfectionaryAdminTheme {
        ChooseCustomerForOrderButton {}
    }
}