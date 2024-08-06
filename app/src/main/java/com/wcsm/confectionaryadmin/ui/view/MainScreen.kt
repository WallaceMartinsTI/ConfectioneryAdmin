package com.wcsm.confectionaryadmin.ui.view

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.components.CustomStatus
import com.wcsm.confectionaryadmin.ui.components.DateTimeContainer
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackground
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus
import com.wcsm.confectionaryadmin.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    val showChooseWhatWillCreateDialog by mainViewModel.showChooseWhatWillCreateDialog.collectAsState()

    val customBlur = if(showChooseWhatWillCreateDialog) 8.dp else 0.dp

    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(AppBackground)
                .fillMaxSize()
                .blur(customBlur),
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
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            DateTimeContainer()

            Spacer(modifier = Modifier.height(60.dp))

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
                    quantity = 2
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_confirmed),
                    color = ConfirmedStatus,
                    quantity = 3
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_in_production),
                    color = InProductionStatus,
                    quantity = 2
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_finished),
                    color = FinishedStatus,
                    quantity = 1
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_delivered),
                    color = DeliveredStatus,
                    quantity = 15
                )
                CustomStatus(
                    text = stringResource(id = R.string.status_cancelled),
                    color = CancelledStatus,
                    quantity = 3
                )
            }
        }

        if(showChooseWhatWillCreateDialog) {
            Dialog(
                onDismissRequest = {
                    mainViewModel.changeShowChooseWhatWillCreateDialog(status = false)
                }
            ) {
                ChooseWhatWillCreateDialog(
                    modifier = Modifier.align(Alignment.Center),
                    onCreateOrderOptionClick = {},
                    onCreateCustomerOptionClick = {},
                    onDissmissDialog = {
                        mainViewModel.changeShowChooseWhatWillCreateDialog(status = false)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ConfectionaryAdminTheme {
        val paddingValues = PaddingValues()
        MainScreen(
            paddingValues = paddingValues,
            mainViewModel = viewModel()
        )
    }
}

@Composable
fun ChooseWhatWillCreateDialog(
    modifier: Modifier = Modifier,
    onCreateOrderOptionClick: () -> Unit,
    onCreateCustomerOptionClick: () -> Unit,
    onDissmissDialog: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(InvertedAppBackground)
            .padding(top = 16.dp, start = 12.dp, end = 12.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.choose_an_option_below),
                color = Color.White,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
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

        ChooseWhatWillCreateButton(
            text = stringResource(id = R.string.btn_text_create_order),
            icon = Icons.Default.PlaylistAdd
        ) {
            onCreateOrderOptionClick()
        }
        
        ChooseWhatWillCreateButton(
            text = stringResource(id = R.string.btn_text_create_customer),
            icon = Icons.Default.PersonAddAlt1,
        ) {
            onCreateCustomerOptionClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseWhatWillCreateDialogPreview() {
    ConfectionaryAdminTheme {
        ChooseWhatWillCreateDialog(
            onCreateOrderOptionClick = {},
            onCreateCustomerOptionClick = {},
            onDissmissDialog = {}
        )
    }
}

@Composable
fun ChooseWhatWillCreateButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    width: Dp = 290.dp,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .width(width)
            .clip(RoundedCornerShape(15.dp))
            .background(ButtonBackground)
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Primary
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseWhatWillCreateButtonPreview() {
    ConfectionaryAdminTheme {
        Column(
            modifier = Modifier
                .height(150.dp)
                .width(350.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ChooseWhatWillCreateButton(
                text = "CRIAR PEDIDO",
                icon = Icons.Default.PlaylistAdd,
            ) {}

            Spacer(modifier = Modifier.height(12.dp))

            ChooseWhatWillCreateButton(
                text = "CRIAR CLIENTE",
                icon = Icons.Default.PostAdd,
            ) {}
        }
    }
}