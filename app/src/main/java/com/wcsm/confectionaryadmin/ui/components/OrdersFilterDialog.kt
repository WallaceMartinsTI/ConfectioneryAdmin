package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.types.FilterType
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.StrongDarkPurple
import com.wcsm.confectionaryadmin.ui.theme.TextFieldBackground
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersFilterDialog(
    ordersViewModel: OrdersViewModel,
    onDissmissDialog: () -> Unit
) {
    var dialogSelected by remember { mutableStateOf<FilterType?>(null) }

    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    var statusDropdownExpanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf("Selecione um status") }

    var filterResult by remember { mutableStateOf("Selecione um filtro") }

    LaunchedEffect(selectedDate, selectedStatus) {
        filterResult = when {
            selectedDate.isNotEmpty() -> selectedDate
            selectedStatus != "Selecione um status" -> selectedStatus
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
                isSelected = dialogSelected == FilterType.DATE
            ) {
                dialogSelected = FilterType.DATE
                showDatePickerDialog = true
                filterResult = "Selecione um filtro"
                selectedStatus = "Selecione um status"
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomRadioButton(
                text = stringResource(id = R.string.textfield_label_status),
                isSelected = dialogSelected == FilterType.STATUS
            ) {
                dialogSelected = FilterType.STATUS
                filterResult = "Selecione um filtro"
                selectedDate = ""
            }

            if(dialogSelected == FilterType.DATE && showDatePickerDialog) {
                MonthYearPickerDialog(
                    ordersViewModel = ordersViewModel,
                    onDissmissDialog = { showDatePickerDialog = false }
                ) { month, year ->
                    selectedDate = "$month/$year"
                }
            }

            if(dialogSelected == FilterType.STATUS) {
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
                        expanded = dialogSelected == FilterType.STATUS && statusDropdownExpanded,
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
                if(dialogSelected != null) {
                    ordersViewModel.updateFilterType(dialogSelected!!)
                }
                ordersViewModel.updateFilterResult(newResult = filterResult)
                onDissmissDialog()
            }
        }
    }
}

@Preview
@Composable
private fun OrdersFilterDialogPreview(ordersViewModel: OrdersViewModel = hiltViewModel()) {
    ConfectionaryAdminTheme {
        OrdersFilterDialog(
            ordersViewModel = ordersViewModel) {}
    }
}