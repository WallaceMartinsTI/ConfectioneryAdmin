package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus

@Composable
fun CustomStatus(
    text: String,
    color: Color,
    quantity: Int
) {
    Row {
        CustomStatusText(text = "$text: ", color = color)
        CustomStatusText(text = "$quantity", color = color)
    }
}

@Composable
fun CustomStatusText(
    text: String,
    color: Color
) {
    Text(
        text = text,
        color = color,
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}

@Preview(showBackground = true)
@Composable
fun CustomStatusPreview() {
    ConfectionaryAdminTheme {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            CustomStatus(text = "Orçamentos", color = QuotationStatus, quantity = 2)
            CustomStatus(text = "Confirmados", color = ConfirmedStatus, quantity = 3)
            CustomStatus(text = "Em Produção", color = InProductionStatus, quantity = 2)
            CustomStatus(text = "Finalizados", color = FinishedStatus, quantity = 1)
            CustomStatus(text = "Entregues", color = DeliveredStatus, quantity = 15)
            CustomStatus(text = "Cancelados", color = CancelledStatus, quantity = 3)
        }
    }
}