package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.components.CustomStatus
import com.wcsm.confectionaryadmin.ui.components.DateTimeContainer
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .background(AppBackground)
            .fillMaxSize(),
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
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ConfectionaryAdminTheme {
        MainScreen()
    }
}