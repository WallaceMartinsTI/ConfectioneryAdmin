package com.wcsm.confectionaryadmin.ui.util

import androidx.compose.ui.graphics.Color
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatus
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatus
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatus
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatus
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatus
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toBrazillianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}

fun Double.toBRL(): String {
    val ptBR = Locale("pt", "BR")
    return NumberFormat.getCurrencyInstance(ptBR).format(this)
}

fun OrderStatus.toStatusString(): String {
    return when(this) {
        OrderStatus.QUOTATION -> "Orçamento"
        OrderStatus.CONFIRMED -> "Confirmado"
        OrderStatus.IN_PRODUCTION -> "Em Produção"
        OrderStatus.FINISHED -> "Finalizado"
        OrderStatus.DELIVERED -> "Entregue"
        OrderStatus.CANCELLED -> "Cancelado"
    }
}

fun OrderStatus.getStatusColor(): Color {
    return when(this) {
        OrderStatus.QUOTATION -> QuotationStatus
        OrderStatus.CONFIRMED -> ConfirmedStatus
        OrderStatus.IN_PRODUCTION -> InProductionStatus
        OrderStatus.FINISHED -> FinishedStatus
        OrderStatus.DELIVERED -> DeliveredStatus
        OrderStatus.CANCELLED -> CancelledStatus
    }
}