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
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatusColor
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatusColor
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatusColor
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatusColor
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatusColor
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatusColor

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
private fun CustomStatusText(
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
private fun CustomStatusPreview() {
    ConfectionaryAdminTheme {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            CustomStatus(text = "Orçamentos", color = QuotationStatusColor, quantity = 2)
            CustomStatus(text = "Confirmados", color = ConfirmedStatusColor, quantity = 3)
            CustomStatus(text = "Em Produção", color = InProductionStatusColor, quantity = 2)
            CustomStatus(text = "Finalizados", color = FinishedStatusColor, quantity = 1)
            CustomStatus(text = "Entregues", color = DeliveredStatusColor, quantity = 15)
            CustomStatus(text = "Cancelados", color = CancelledStatusColor, quantity = 3)
        }
    }
}