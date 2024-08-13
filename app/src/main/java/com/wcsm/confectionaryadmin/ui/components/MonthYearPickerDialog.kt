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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.util.capitalizeFirstLetter
import com.wcsm.confectionaryadmin.ui.util.getCurrentMonth
import com.wcsm.confectionaryadmin.ui.util.getCurrentYear

@Composable
fun MonthYearPickerDialog(
    onDissmissDialog: () -> Unit,
    onMonthYearSelected: (month: String, year: Int) -> Unit
) {
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
        onDismissRequest = { onDissmissDialog() }
    ) {
        Column(
            modifier = Modifier
                .width(500.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(brush = InvertedAppBackground)
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = Primary)
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
                    color = Primary,
                    textAlign = TextAlign.Center,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = Primary)
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
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = Primary)
                        .width(60.dp)
                        .height(40.dp)
                        .clickable { year-- }
                )

                Text(
                    text = year.toString(),
                    color = Primary,
                    textAlign = TextAlign.Center,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = Primary)
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
                    onDissmissDialog()
                }

                PrimaryButton(
                    text = "Escolher data",
                    width = 150.dp
                ) {
                    onMonthYearSelected(monthList[monthIndex], year)
                    onDissmissDialog()
                }
            }
        }
    }
}

@Preview
@Composable
fun MonthYearPickerDialogPreview() {
    ConfectionaryAdminTheme {
        MonthYearPickerDialog(
            onDissmissDialog = {},
            onMonthYearSelected = { _, _ -> }
        )
    }
}