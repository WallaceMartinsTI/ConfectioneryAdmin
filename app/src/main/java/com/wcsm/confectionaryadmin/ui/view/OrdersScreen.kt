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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import com.wcsm.confectionaryadmin.ui.components.CustomRadioButton
import com.wcsm.confectionaryadmin.ui.components.OrderCard
import com.wcsm.confectionaryadmin.ui.components.OrdersFilterContainer
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.BrownColor
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.LightDarkPurple
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurple
import com.wcsm.confectionaryadmin.ui.theme.TextFieldBackground
import com.wcsm.confectionaryadmin.ui.theme.ValueColor
import com.wcsm.confectionaryadmin.ui.util.toBrazillianDateFormat
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

val ordersMock = listOf(
    Order(
        id = 0,
        title = "Bolo Bento Cake",
        description = "Recheio de chocolate e mousse de morango",
        price = 120.50,
        status = OrderStatus.QUOTATION,
        orderDate = "15/02/2024 16:30",
        deliverDate = "20/02/2024 17:25"
    ),
    Order(
        id = 1,
        title = "Doce Brigadeiro 100u",
        description = "100 unidades de Brigadeiros skajdhiashdisahudiuhasiudhas suadhiashd idsahduias uiash iusah iduashi sadasdasd",
        price = 115.00,
        status = OrderStatus.CONFIRMED,
        orderDate = "25/02/2024 12:30",
        deliverDate = "28/02/2024 16:22"
    ),
    Order(
        id = 2,
        title = "Bolo de Aniversário",
        description = "Massa de Chocolate e recheio de prestígio",
        price = 95.25,
        status = OrderStatus.IN_PRODUCTION,
        orderDate = "28/03/2024 09:30",
        deliverDate = "10/04/2024 13:15"
    ),
    Order(
        id = 3,
        title = "Bolo de Aniversário com nome meio grande vamos ver",
        description = "Massa de Chocolate e recheio de prestígio",
        price = 95.25,
        status = OrderStatus.DELIVERED,
        orderDate = "28/03/2024 11:00",
        deliverDate = "10/04/2024 16:00"
    ),
    Order(
        id = 0,
        title = "Bolo Bento Cake",
        description = "Recheio de chocolate e mousse de morango",
        price = 120.50,
        status = OrderStatus.QUOTATION,
        orderDate = "15/02/2024 16:30",
        deliverDate = "20/02/2024 17:25"
    ),
    Order(
        id = 1,
        title = "Doce Brigadeiro 100u",
        description = "100 unidades de Brigadeiros skajdhiashdisahudiuhasiudhas suadhiashd idsahduias uiash iusah iduashi sadasdasd",
        price = 115.00,
        status = OrderStatus.CONFIRMED,
        orderDate = "25/02/2024 12:30",
        deliverDate = "28/02/2024 16:22"
    ),
    Order(
        id = 2,
        title = "Bolo de Aniversário",
        description = "Massa de Chocolate e recheio de prestígio",
        price = 95.25,
        status = OrderStatus.IN_PRODUCTION,
        orderDate = "28/03/2024 09:30",
        deliverDate = "10/04/2024 13:15"
    ),
    Order(
        id = 3,
        title = "Bolo de Aniversário com nome meio grande vamos ver",
        description = "Massa de Chocolate e recheio de prestígio",
        price = 95.25,
        status = OrderStatus.DELIVERED,
        orderDate = "28/03/2024 11:00",
        deliverDate = "10/04/2024 16:00"
    )
)

@Composable
fun OrdersScreen(
    paddingValues: PaddingValues,
    ordersViewModel: OrdersViewModel = viewModel()
) {
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    var showFilterDialog by remember { mutableStateOf(false) }

    val customBlur = if(showFilterDialog) 8.dp else 0.dp

    val filterResult by ordersViewModel.filterResult.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(AppBackground)
                .fillMaxSize()
                .blur(customBlur),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.screen_title_orders_screen),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                style = TextStyle(
                    brush = AppTitleGradient
                ),
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.ChangeCircle,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            // Toggle Orders order by date
                        }
                )

                OrdersFilterContainer(
                    text = filterResult.ifEmpty {
                        null
                    }
                ) {
                    showFilterDialog = true
                }

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            ordersViewModel.updateFilterResult(newResult = "")
                        }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier.width(300.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(ordersMock) {
                    OrderCard(
                        order = it,
                        isExpanded = expandedStates[it.id] ?: false,
                        onExpandChange = { expanded ->
                            expandedStates[it.id] = expanded
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if(showFilterDialog) {
            Dialog(onDismissRequest = { showFilterDialog = false }) {
                OrdersFilterDialog(ordersViewModel = ordersViewModel) {
                    showFilterDialog = false
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersFilterDialog(
    ordersViewModel: OrdersViewModel,
    onDissmissDialog: () -> Unit
) {
    var dialogSelected by remember { mutableStateOf("") }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var statusDropdownExpanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("Selecione um status") }

    var filterResult by remember { mutableStateOf("Selecione um filtro") }

    LaunchedEffect(selectedDate, selectedStatus) {
        filterResult = when {
            selectedDate.isNotEmpty() -> "Data: $selectedDate"
            selectedStatus != "Selecione um status" -> "Status: $selectedStatus"
            else -> "Selecione um filtro"
        }
    }

    Column(
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(brush = InvertedAppBackground)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.filter_by),
                color = Primary,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onDissmissDialog() },
                tint = Primary
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomRadioButton(
                text = stringResource(id = R.string.date),
                isSelected = dialogSelected == "DATA"
            ) {
                dialogSelected = "DATA"
                showDatePickerDialog = true
                filterResult = "Selecione um filtro"
                selectedStatus = "Selecione um status"
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomRadioButton(
                text = stringResource(id = R.string.textfield_label_status),
                isSelected = dialogSelected == "STATUS"
            ) {
                dialogSelected = "STATUS"
                filterResult = "Selecione um filtro"
                selectedDate = ""
            }

            if(dialogSelected == "DATA" && showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePickerDialog = false
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    selectedDate = millis.toBrazillianDateFormat()
                                }
                                showDatePickerDialog = false
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
                        state = datePickerState,
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
            if(dialogSelected == "STATUS") {
                val colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = TextFieldBackground,
                    unfocusedContainerColor = TextFieldBackground,
                    errorContainerColor = TextFieldBackground,
                    cursorColor = Primary,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Primary,
                    selectionColors = TextSelectionColors(
                        Primary, Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box {
                    ExposedDropdownMenuBox(
                        expanded = dialogSelected == "STATUS" && statusDropdownExpanded,
                        onExpandedChange = {
                            statusDropdownExpanded = !statusDropdownExpanded
                        }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .menuAnchor()
                                .height(50.dp)
                                .width(250.dp),
                            value = selectedStatus,
                            onValueChange = {
                                statusDropdownExpanded = !statusDropdownExpanded
                            },
                            textStyle = TextStyle(
                                color = Primary,
                                fontFamily = InterFontFamily,
                                fontSize = 18.sp
                            ),
                            colors = colors,
                            singleLine = true,
                            readOnly = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.None
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector =
                                    if(statusDropdownExpanded) Icons.Filled.KeyboardArrowUp
                                    else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Primary
                                )
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = statusDropdownExpanded,
                            onDismissRequest = {
                                statusDropdownExpanded = false
                            },
                            modifier = Modifier.background(color = StrongDarkPurple)
                        ) {
                            val statusOptions = listOf(
                                stringResource(id = R.string.status_quotation),
                                stringResource(id = R.string.status_confirmed),
                                stringResource(id = R.string.status_in_production),
                                stringResource(id = R.string.status_finished),
                                stringResource(id = R.string.status_delivered),
                                stringResource(id = R.string.status_cancelled)
                            )

                            statusOptions.forEach {
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
                                        selectedStatus = it
                                        statusDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp),
                value = filterResult,
                enabled = false,
                onValueChange = {
                    statusDropdownExpanded = !statusDropdownExpanded
                },
                textStyle = TextStyle(
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontSize = 18.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = TextFieldBackground,
                    unfocusedContainerColor = TextFieldBackground,
                    errorContainerColor = TextFieldBackground,
                    cursorColor = Primary,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Primary,
                    selectionColors = TextSelectionColors(
                        Primary, Color.Transparent
                    )
                ),
            )

            PrimaryButton(
                text = "CONFIRMAR",
                width = 200.dp,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                ordersViewModel.updateFilterResult(newResult = filterResult)
                onDissmissDialog()
            }
        }
        
    }
}

@Preview(showBackground = true)
@Composable
private fun OrdersScreenPreview() {
    ConfectionaryAdminTheme {
        val paddingValues = PaddingValues()
        OrdersScreen(paddingValues)
    }
}



@Preview
@Composable
private fun OrdersFilterDialogPreview() {
    ConfectionaryAdminTheme {
        OrdersFilterDialog(ordersViewModel = viewModel()) {}
    }
}