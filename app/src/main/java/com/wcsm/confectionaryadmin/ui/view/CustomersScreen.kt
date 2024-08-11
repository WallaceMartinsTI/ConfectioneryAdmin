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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.CustomerWithOrders
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.MinimizedCustomerCard
import com.wcsm.confectionaryadmin.ui.components.OrderCard
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.BrownColor
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.GrayColor
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.LightRed
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurple
import com.wcsm.confectionaryadmin.ui.theme.TextFieldBackground
import com.wcsm.confectionaryadmin.ui.util.toBrazillianDateFormat
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel

val customersMock = listOf(
    Customer(
        customerId = 0,
        name = "Carlos Maia",
        email = "carlos.maia@hotmail.com",
        phone = "31997862543",
        gender = "Masculino",
        dateOfBirth = "23/10/1998",
        address = "Rua José Malvo, Nº 32, Guabas - Betim MG",
        notes = "Não gosta de abacaxi"
    ),
    Customer(
        customerId = 1,
        name = "Isabella Silva",
        email = "isabella.silva@gmail.com",
        phone = "31976357843",
        gender = "Feminino",
        dateOfBirth = "12/05/2002",
        address = "Rua Malvino Costa, Nº 21, Mecs - Belo Horizonte MG",
        notes= "Não gosta de morango"
    ),
    Customer(
        customerId = 2,
        name = "Ana Maria",
        email = "ana.maria@hotmail.com",
        phone = "31976487624",
        gender = "Feminino",
        dateOfBirth = "07/12/2000",
        address = "Av. Costa Silva, Nº 735, Quatis - Contagem MG",
        notes= ""
    ),
    Customer(
        customerId = 3,
        name = "Julesca Matil",
        email = "julesca.metil@gmail.com",
        phone = "31974826374",
        gender = "Outros",
        dateOfBirth = "10/09/1982",
        address = "Rua Paris, Nº 8, Kansas - Betim MG",
        notes= "Não gosta de castanha"
    ),
    Customer(
        customerId = 4,
        name = "João Silva",
        email = "joao.silva@hotmail.com",
        phone = "31978652738",
        gender = "Masculino",
        dateOfBirth = "29/01/1999",
        address = "Av. Brasil, Nº 32, Centro - Belo Horizonte MG",
        notes= ""
    )
)
val customersWithOthersMock = listOf(
    CustomerWithOrders(
        customer = customersMock[0],
        orders = ordersMock
    )
)

@Composable
fun CustomersScreen(
    paddingValues: PaddingValues,
    customersViewModel: CustomersViewModel = hiltViewModel()
) {
    val customersWithOrders by customersViewModel.customersWithOrders.collectAsState()

    var searchInput by remember { mutableStateOf("") }
    var selectedCustomer by remember { mutableStateOf<CustomerWithOrders?>(null) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(AppBackground)
                .fillMaxSize()
                .blur(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.screen_title_customers_screen),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                style = TextStyle(
                    brush = AppTitleGradient
                ),
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            FilterCustomer(
                leadingIcon = if(searchInput.isEmpty()) Icons.Default.Search else Icons.Default.ArrowBack,
                trailingIcon = if(searchInput.isNotEmpty()) Icons.Default.Clear else null,
                value = searchInput,
                onLeadingIconClick = {
                    if(searchInput.isNotEmpty()) { focusManager.clearFocus() }
                },
                onTrailingIconClick = {
                    searchInput = ""
                    focusRequester.requestFocus()
                },
                onValueChange = {
                    searchInput = it
                },
                modifier = Modifier.focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier.width(300.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(customersWithOrders) {
                    MinimizedCustomerCard(
                        customer = it.customer,
                        expandIcon = true
                    ) {
                        selectedCustomer = it
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if(selectedCustomer != null) {
            Column {
                ExpandedCustomerScreen(
                    customersWithOrders = selectedCustomer!!
                ) {
                    selectedCustomer = null
                }
            }
        }
    }
}

@Composable
private fun FilterCustomer(
    leadingIcon: ImageVector,
    trailingIcon: ImageVector?,
    value: String,
    modifier: Modifier = Modifier,
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit,
    onValueChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = modifier.width(280.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Primary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                Primary, Color.Transparent
            )
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
                color = GrayColor
            )
        },
        textStyle = TextStyle(
            color = GrayColor,
            fontFamily = InterFontFamily,
            fontSize = 18.sp
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = GrayColor,
                modifier = Modifier.clickable { onLeadingIconClick() }
            )
        },
        trailingIcon = {
            if(trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = GrayColor,
                    modifier = Modifier.clickable { onTrailingIconClick() }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedCustomerScreen(
    customersWithOrders: CustomerWithOrders,
    modifier: Modifier = Modifier,
    onDissmiss: () -> Unit
) {
    val customer = customersWithOrders.customer
    val orders = customersWithOrders.orders

    var name by rememberSaveable { mutableStateOf(customer.name) }
    var email by rememberSaveable { mutableStateOf(customer.email ?: "") }
    var phone by rememberSaveable { mutableStateOf(customer.phone ?: "") }
    var gender by rememberSaveable { mutableStateOf(customer.gender ?: "") }
    var dateOfBirth by rememberSaveable { mutableStateOf(customer.dateOfBirth ?: "") }
    var address by rememberSaveable { mutableStateOf(customer.address ?: "") }
    var notes by rememberSaveable { mutableStateOf(customer.notes ?: "") }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    var genderDropdownExpanded by remember { mutableStateOf(false) }
    var genderSelected by remember { mutableStateOf("Selecione um gênero") }

    var isCustomerOrdersOpen by remember { mutableStateOf(false) }

    val customBlur = if(isCustomerOrdersOpen) 8.dp else 0.dp

    val customerIcon = when(customer.gender) {
        "Masculino" -> painterResource(id = R.drawable.male)
        "Feminino" -> painterResource(id = R.drawable.female)
        else -> painterResource(id = R.drawable.others)
    }

    LaunchedEffect(selectedDate) {
        if(selectedDate.isNotEmpty()) {
            dateOfBirth = selectedDate
        }
    }
    
    LaunchedEffect(genderSelected) {
        if(genderSelected != "Selecione um gênero") {
            gender = genderSelected
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = InvertedAppBackground)
                .blur(customBlur),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Icon(
                    painter = customerIcon,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                Text(
                    text = customer.name,
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { onDissmiss() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_name),
                    placeholder = stringResource(id = R.string.textfield_placeholder_name),
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    errorMessage = null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    value = name
                ) {
                    name = it
                }

                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_email),
                    placeholder = stringResource(id = R.string.textfield_placeholder_email),
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    errorMessage = null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    value = email
                ) {
                    email = it
                }

                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_phone),
                    placeholder = stringResource(id = R.string.textfield_placeholder_phone),
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                    errorMessage = null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    value = phone
                ) {
                    phone = it
                }

                // GENDER DROPDOWN MENU
                Box {
                    ExposedDropdownMenuBox(
                        expanded = genderDropdownExpanded,
                        onExpandedChange = {
                            genderDropdownExpanded = !genderDropdownExpanded
                        }
                    ) {
                        CustomTextField(
                            modifier = Modifier.menuAnchor(),
                            label = stringResource(id = R.string.textfield_label_gender),
                            placeholder = "",
                            errorMessage = null,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector =
                                    if(genderDropdownExpanded) Icons.Filled.KeyboardArrowUp
                                    else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Primary
                                )
                            },
                            singleLine = true,
                            readOnly = true,
                            value = gender
                        ) {
                            genderDropdownExpanded = !genderDropdownExpanded
                        }

                        ExposedDropdownMenu(
                            expanded = genderDropdownExpanded,
                            onDismissRequest = { genderDropdownExpanded = false },
                            modifier = Modifier.background(color = StrongDarkPurple)
                        ) {
                            val genderOptions = listOf(
                                stringResource(id = R.string.gender_male),
                                stringResource(id = R.string.gender_female),
                                stringResource(id = R.string.gender_other)
                            )

                            genderOptions.forEach {
                                val icon = when(it) {
                                    "Masculino" -> Icons.Default.Male
                                    "Feminino" -> Icons.Default.Female
                                    else -> Icons.Default.Transgender
                                }
                                DropdownMenuItem(
                                    text = {
                                           Text(
                                               text = it,
                                               color = Color.White,
                                               fontFamily = InterFontFamily,
                                               fontWeight = FontWeight.SemiBold
                                           )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    },
                                    onClick = {
                                        genderSelected = it
                                        genderDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }



                if(showDatePickerDialog) {
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

                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_date_of_birth),
                    placeholder = "",
                    modifier = Modifier
                        .onFocusEvent {
                          if(it.isFocused) {
                              showDatePickerDialog = true
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
                    value = dateOfBirth
                ) { }

                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_address),
                    placeholder = stringResource(id = R.string.textfield_placeholder_address),
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    singleLine = false,
                    errorMessage = null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddLocation,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    value = address
                ) {
                    address = it
                }

                CustomTextField(
                    label = stringResource(id = R.string.textfield_label_notes),
                    placeholder = stringResource(id = R.string.textfield_placeholder_notes),
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    singleLine = false,
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
                    value = notes
                ) {
                    notes = it
                }

                PrimaryButton(text = stringResource(id = R.string.btn_text_save_edit)) {
                    // SAVE CUSTOMER UPDATES
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(text = stringResource(id = R.string.btn_text_show_orders)) {
                isCustomerOrdersOpen = true
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomDeleteButton(text = stringResource(id = R.string.btn_text_delete)) {

            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        if(isCustomerOrdersOpen) {
            Dialog(onDismissRequest = { isCustomerOrdersOpen = false }) {
                CustomerOrders(
                    customer = customer,
                    orders = orders
                ) {
                    isCustomerOrdersOpen = false
                }
            }
        }
    }
}

@Composable
private fun CustomDeleteButton(
    text: String,
    modifier: Modifier = Modifier,
    width: Dp = 290.dp,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .width(width)
            .clip(RoundedCornerShape(15.dp))
            .background(LightRed)
            .border(
                width = 1.dp,
                color = Primary,
                shape = RoundedCornerShape(15.dp)
            )
            .height(50.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun CustomerOrders(
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
                    onDelete = {},
                    isExpanded = expandedStates[it.orderId] ?: false
                ) { expanded ->
                    expandedStates[it.orderId] = expanded
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomersScreenPreview() {
    ConfectionaryAdminTheme {
        val paddingValues = PaddingValues()
        CustomersScreen(paddingValues)
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterCustomerPreview() {
    ConfectionaryAdminTheme {
        FilterCustomer(
            leadingIcon = Icons.Default.Search,
            trailingIcon = null,
            value = "",
            onLeadingIconClick = {},
            onTrailingIconClick = {},
            onValueChange = {}
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun ExpandedCustomerScreenPreview() {
    ConfectionaryAdminTheme {
        ExpandedCustomerScreen(
            customersWithOrders = customersWithOthersMock[0]
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomDeleteButtonPreview() {
    ConfectionaryAdminTheme {
        CustomDeleteButton("DEKETAR") {}
    }
}

@Preview
@Composable
private fun CustomerOrdersPreview() {
    ConfectionaryAdminTheme {
        CustomerOrders(
            customer = customersMock[0],
            orders = ordersMock
        ) {}
    }
}