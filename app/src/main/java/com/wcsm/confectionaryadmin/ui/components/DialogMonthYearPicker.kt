package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.types.OrderDateType
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackgroundColor
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor
import com.wcsm.confectionaryadmin.ui.util.capitalizeFirstLetter
import com.wcsm.confectionaryadmin.ui.util.getCurrentMonth
import com.wcsm.confectionaryadmin.ui.util.getCurrentYear
import com.wcsm.confectionaryadmin.ui.viewmodel.OrdersViewModel

@Composable
fun DialogMonthYearPicker(
    ordersViewModel: OrdersViewModel,
    onDismissDialog: () -> Unit,
    onMonthYearSelected: (month: String, year: Int) -> Unit
) {
    val orderOrDeliverDateSelected by ordersViewModel.orderDateType.collectAsState()

    val monthList = listOf(
        stringResource(id = R.string.month_january), stringResource(id = R.string.month_february),
        stringResource(id = R.string.month_march), stringResource(id = R.string.month_april),
        stringResource(id = R.string.month_may), stringResource(id = R.string.month_june),
        stringResource(id = R.string.month_july), stringResource(id = R.string.month_august),
        stringResource(id = R.string.month_september), stringResource(id = R.string.month_october),
        stringResource(id = R.string.month_november), stringResource(id = R.string.month_december)
    )

    val month by remember { mutableStateOf(getCurrentMonth(ptBr = true)) }
    var monthIndex by remember {
        mutableIntStateOf(monthList.indexOf(capitalizeFirstLetter(month)))
    }

    var year by remember { mutableIntStateOf(getCurrentYear()) }

    Dialog(
        onDismissRequest = { onDismissDialog() }
    ) {
        Column(
            modifier = Modifier
                .width(500.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(brush = InvertedAppBackgroundColor)
                .padding(12.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.month_title),
                color = Color.White,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Data para o filtro:",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Row(
                        modifier = Modifier.clickable {
                            ordersViewModel.updateOrderDateType(OrderDateType.ORDER_DATE)
                        },
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector =
                            if(orderOrDeliverDateSelected == OrderDateType.ORDER_DATE)
                                Icons.Default.RadioButtonChecked
                            else
                                Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = PrimaryColor
                        )

                        Text(
                            text = "PEDIDO",
                            color = PrimaryColor,
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Row(
                        modifier = Modifier.clickable {
                            ordersViewModel.updateOrderDateType(OrderDateType.DELIVER_DATE)
                        },
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector =
                            if(orderOrDeliverDateSelected == OrderDateType.DELIVER_DATE )
                                Icons.Default.RadioButtonChecked
                            else
                                Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = PrimaryColor
                        )

                        Text(
                            text = "ENTREGA",
                            color = PrimaryColor,
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = PrimaryColor)
                        .width(60.dp)
                        .height(40.dp)
                        .clickable {
                            if (monthIndex == 0) {
                                monthIndex = 11
                            } else {
                                monthIndex--
                            }
                        }
                )

                Text(
                    text = monthList[monthIndex],
                    color = PrimaryColor,
                    textAlign = TextAlign.Center,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = PrimaryColor)
                        .width(60.dp)
                        .height(40.dp)
                        .clickable {
                            if (monthIndex == 11) {
                                monthIndex = 0
                            } else {
                                monthIndex++
                            }
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = PrimaryColor)
                        .width(60.dp)
                        .height(40.dp)
                        .clickable { year-- }
                )

                Text(
                    text = year.toString(),
                    color = PrimaryColor,
                    textAlign = TextAlign.Center,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = PrimaryColor)
                        .width(60.dp)
                        .height(40.dp)
                        .clickable { year++ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                PrimaryButton(
                    text = "Cancelar",
                    width = 100.dp
                ) {
                    onDismissDialog()
                }

                PrimaryButton(
                    text = "Escolher data",
                    width = 150.dp
                ) {
                    onMonthYearSelected(monthList[monthIndex], year)
                    onDismissDialog()
                }
            }
        }
    }
}

@Preview
@Composable
private fun MonthYearPickerDialogPreview(
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    ConfectionaryAdminTheme {
        DialogMonthYearPicker(
            ordersViewModel = ordersViewModel,
            onDismissDialog = {},
            onMonthYearSelected = { _, _ -> }
        )
    }
}