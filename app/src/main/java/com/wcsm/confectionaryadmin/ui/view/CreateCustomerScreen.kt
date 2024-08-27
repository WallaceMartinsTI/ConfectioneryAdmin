package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.CustomTopAppBar
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackgroundColor
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurpleColor
import com.wcsm.confectionaryadmin.ui.util.PhoneNumberVisualTransformation
import com.wcsm.confectionaryadmin.ui.util.toBrazillianDateFormat
import com.wcsm.confectionaryadmin.ui.viewmodel.CreateCustomerViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCustomerScreen(
    navController: NavController,
    customersViewModel: CustomersViewModel,
    createCustomerViewModel: CreateCustomerViewModel = hiltViewModel()
) {
    val customerState by createCustomerViewModel.customerState.collectAsState()
    val newCustomerCreate by createCustomerViewModel.newCustomerCreated.collectAsState()
    val customerSyncState by customersViewModel.customerSyncState.collectAsState()

    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by rememberSaveable { mutableStateOf("") }

    var genderDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var gender by rememberSaveable { mutableStateOf("Selecione o gênero") }

    val focusRequester = rememberSaveable { List(5) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(newCustomerCreate) {
        if(newCustomerCreate) {
            createCustomerViewModel.updateNewCustomerCreated(false)
            customersViewModel.getAllCustomers()
            customersViewModel.updateCustomerSyncState(
                customerSyncState.copy(
                    isSincronized = false
                )
            )
            navController.navigate(Screen.Customers.route)
        }
    }

    LaunchedEffect(selectedDate) {
        if(selectedDate.isNotEmpty()) {
            createCustomerViewModel.updateCreateCustomerState(
                customerState.copy(
                    dateOfBirth = selectedDate
                )
            )
        }
    }

    LaunchedEffect(customerState.gender) {
        gender = customerState.gender.ifEmpty {
            "Selecione o gênero"
        }
    }

    LaunchedEffect(customerState.name) {
        if(customerState.name.isNotEmpty()) {
            createCustomerViewModel.updateCreateCustomerState(
                customerState.copy(
                    nameErrorMessage = null
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopAppBar(
            title = stringResource(id = R.string.screen_title_create_customer_screen)
        ) {
            navController.popBackStack()
        }

        ScreenDescription(
            description = stringResource(id = R.string.create_customer_screen_description),
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
            CustomTextField(
                label = stringResource(id = R.string.textfield_label_name),
                placeholder = stringResource(id = R.string.textfield_placeholder_name),
                modifier = Modifier.focusRequester(focusRequester[0]),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                errorMessage = customerState.nameErrorMessage,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if(customerState.name.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                createCustomerViewModel.updateCreateCustomerState(
                                    customerState.copy(
                                        name = ""
                                    )
                                )
                                focusRequester[0].requestFocus()
                            }
                        )
                    }
                },
                value = customerState.name
            ) {
                createCustomerViewModel.updateCreateCustomerState(
                    customerState.copy(
                        name = it
                    )
                )
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_email),
                placeholder = stringResource(id = R.string.textfield_placeholder_email),
                modifier = Modifier.focusRequester(focusRequester[1]),
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
                    if(customerState.email.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                createCustomerViewModel.updateCreateCustomerState(
                                    customerState.copy(
                                        email = ""
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
                value = customerState.email
            ) {
                createCustomerViewModel.updateCreateCustomerState(
                    customerState.copy(
                        email = it
                    )
                )
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_phone),
                placeholder = stringResource(id = R.string.textfield_placeholder_phone),
                modifier = Modifier.focusRequester(focusRequester[2]),
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next,
                visualTransformation = PhoneNumberVisualTransformation(),
                errorMessage = null,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if(customerState.phone.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                createCustomerViewModel.updateCreateCustomerState(
                                    customerState.copy(
                                        phone = ""
                                    )
                                )
                                focusRequester[2].requestFocus()
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                },
                value = customerState.phone
            ) { newValue ->
                if(newValue.all { it.isDigit() }) {
                    createCustomerViewModel.updateCreateCustomerState(
                        customerState.copy(
                            phone = newValue.ifEmpty { "" }
                        )
                    )
                }
            }

            if(showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePickerDialog = false
                        focusManager.clearFocus()
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    selectedDate = millis.toBrazillianDateFormat()
                                }
                                showDatePickerDialog = false
                                focusManager.clearFocus()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryColor
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
                            headlineContentColor = PrimaryColor,
                            weekdayContentColor = PrimaryColor,
                            currentYearContentColor = PrimaryColor,
                            selectedYearContainerColor = PrimaryColor,
                            selectedDayContainerColor = PrimaryColor,
                            todayContentColor = PrimaryColor,
                            todayDateBorderColor = PrimaryColor
                        )
                    )
                }
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_date_of_birth),
                placeholder = "",
                modifier = Modifier
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            showDatePickerDialog = true
                        }
                    },
                errorMessage = null,
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
                value = customerState.dateOfBirth
            ) { }

            Box {
                ExposedDropdownMenuBox(
                    expanded = genderDropdownExpanded,
                    onExpandedChange = { genderDropdownExpanded = !genderDropdownExpanded }
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
                                if (genderDropdownExpanded) Icons.Filled.KeyboardArrowUp
                                else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                                tint = PrimaryColor
                            )

                        },
                        readOnly = true,
                        value = gender
                    ) {
                        genderDropdownExpanded = !genderDropdownExpanded
                    }

                    ExposedDropdownMenu(
                        expanded = genderDropdownExpanded,
                        onDismissRequest = { genderDropdownExpanded = false },
                        modifier = Modifier.background(color = StrongDarkPurpleColor)
                    ) {
                        val genderOptions = listOf(
                            stringResource(id = R.string.gender_male),
                            stringResource(id = R.string.gender_female),
                            stringResource(id = R.string.gender_other),
                        )

                        genderOptions.forEach {
                            val genderIcon = when(it) {
                                stringResource(id = R.string.gender_male) -> Icons.Default.Male
                                stringResource(id = R.string.gender_female) -> Icons.Default.Female
                                else -> Icons.Default.PersonOutline
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
                                        imageVector = genderIcon,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                },
                                onClick = {
                                    createCustomerViewModel.updateCreateCustomerState(
                                        customerState.copy(
                                            gender = it
                                        )
                                    )
                                    genderDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }


            CustomTextField(
                label = stringResource(id = R.string.textfield_label_address),
                placeholder = stringResource(id = R.string.textfield_placeholder_address),
                modifier = Modifier.focusRequester(focusRequester[3]),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                errorMessage = null,
                singleLine = false,
                maxLines = 5,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if(customerState.address.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                createCustomerViewModel.updateCreateCustomerState(
                                    customerState.copy(
                                        address = ""
                                    )
                                )
                                focusRequester[3].requestFocus()
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                },
                value = customerState.address
            ) {
                createCustomerViewModel.updateCreateCustomerState(
                    customerState.copy(
                        address = it
                    )
                )
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_notes),
                placeholder = stringResource(id = R.string.textfield_placeholder_notes),
                modifier = Modifier.focusRequester(focusRequester[4]),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                errorMessage = null,
                singleLine = false,
                maxLines = 10,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Note,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if(customerState.notes.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                createCustomerViewModel.updateCreateCustomerState(
                                    customerState.copy(
                                        notes = ""
                                    )
                                )
                                focusRequester[4].requestFocus()
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                },
                value = customerState.notes
            ) {
                createCustomerViewModel.updateCreateCustomerState(
                    customerState.copy(
                        notes = it
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(text = stringResource(id = R.string.btn_text_create_customer)) {
                createCustomerViewModel.saveCustomer()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
private fun CreateCustomerScreenPreview(
    customersViewModel: CustomersViewModel = hiltViewModel()
) {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        CreateCustomerScreen(navController, customersViewModel)
    }
}