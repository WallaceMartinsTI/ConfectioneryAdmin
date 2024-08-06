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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Transgender
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.CustomTopAppBar
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurple
import com.wcsm.confectionaryadmin.ui.util.toBrazillianDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrderScreen(navController: NavController) {
    var customer by remember { mutableStateOf("") }
    var orderName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var orderDate by remember { mutableStateOf("") }
    var deliverDate by remember { mutableStateOf("") }

    var status by remember { mutableStateOf("Orçamento") }
    var statusDropdownExpanded by remember { mutableStateOf(false) }

    var orderDateShowDatePickerDialog by remember { mutableStateOf(false) }
    val orderDateDatePickerState = rememberDatePickerState()
    var orderDateSelectedDate by remember { mutableStateOf("") }

    var deliverDateShowDatePickerDialog by remember { mutableStateOf(false) }
    val deliverDateDatePickerState = rememberDatePickerState()
    var deliverDateSelectedDate by remember { mutableStateOf("") }

    val focusRequester = remember { List(2) { FocusRequester() } }

    LaunchedEffect(orderDateSelectedDate) {
        if(orderDateSelectedDate.isNotEmpty()) {
            orderDate = orderDateSelectedDate
        }
    }

    LaunchedEffect(deliverDateSelectedDate) {
        if(deliverDateSelectedDate.isNotEmpty()) {
            deliverDate = deliverDateSelectedDate
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.screen_title_create_order_screen),
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScreenDescription(
                description = stringResource(id = R.string.create_order_screen_description),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.width(280.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_customer),
                    placeholder = "",
                    modifier = Modifier
                        .focusRequester(focusRequester[0]),
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    width = 200.dp,
                    readOnly = true,
                    errorMessage = null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    value = ""
                ) {
                    //
                }

                Spacer(modifier = Modifier.width(8.dp))

                ChooseCustomerForOrderButton {
                    // Open a list of customers
                }
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_order_name),
                placeholder = stringResource(id = R.string.textfield_placeholder_order_name),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                errorMessage = null,
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
                value = orderName
            ) {
                orderName = it
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_description),
                placeholder = stringResource(id = R.string.textfield_placeholder_description),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                errorMessage = null,
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
                value = description
            ) {
                description = it
            }

            // PRICE

            if(orderDateShowDatePickerDialog) {
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

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_order_request_date),
                placeholder = "",
                modifier = Modifier
                    .onFocusEvent { focusState ->
                      if(focusState.isFocused) {
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
                value = orderDate
            ) { }

            if(deliverDateShowDatePickerDialog) {
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

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_order_deliver_date),
                placeholder = "",
                modifier = Modifier
                    .onFocusEvent { focusState ->
                        if(focusState.isFocused) {
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
                value = deliverDate
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
                                if(statusDropdownExpanded) Icons.Filled.KeyboardArrowUp
                                else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Primary
                            )
                        },
                        singleLine = true,
                        readOnly = true,
                        value = status
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
                                    status = it
                                    statusDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(text = stringResource(id = R.string.btn_text_create_order)) {
                // CREATE ORDER
            }
        }
    }
}

@Composable
private fun ChooseCustomerForOrderButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
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