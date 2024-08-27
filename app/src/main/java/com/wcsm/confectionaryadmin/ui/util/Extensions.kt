package com.wcsm.confectionaryadmin.ui.util

import androidx.compose.ui.graphics.Color
import com.wcsm.confectionaryadmin.data.model.entities.FirestoreUser
import com.wcsm.confectionaryadmin.data.model.entities.User
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus
import com.wcsm.confectionaryadmin.ui.theme.CancelledStatusColor
import com.wcsm.confectionaryadmin.ui.theme.ConfirmedStatusColor
import com.wcsm.confectionaryadmin.ui.theme.DeliveredStatusColor
import com.wcsm.confectionaryadmin.ui.theme.FinishedStatusColor
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatusColor
import com.wcsm.confectionaryadmin.ui.theme.QuotationStatusColor
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

fun String.toOrderStatus(): OrderStatus {
    return when(this) {
        "Orçamento" -> OrderStatus.QUOTATION
        "Confirmado" -> OrderStatus.CONFIRMED
        "Em Produção" -> OrderStatus.IN_PRODUCTION
        "Finalizado" -> OrderStatus.FINISHED
        "Entregue" -> OrderStatus.DELIVERED
        "Cancelado" -> OrderStatus.CANCELLED
        else -> OrderStatus.QUOTATION
    }
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
        OrderStatus.QUOTATION -> QuotationStatusColor
        OrderStatus.CONFIRMED -> ConfirmedStatusColor
        OrderStatus.IN_PRODUCTION -> InProductionStatusColor
        OrderStatus.FINISHED -> FinishedStatusColor
        OrderStatus.DELIVERED -> DeliveredStatusColor
        OrderStatus.CANCELLED -> CancelledStatusColor
    }
}

fun OrderStatus.getNextStatus(): OrderStatus {
    return when(this) {
        OrderStatus.QUOTATION -> OrderStatus.CONFIRMED
        OrderStatus.CONFIRMED -> OrderStatus.IN_PRODUCTION
        OrderStatus.IN_PRODUCTION -> OrderStatus.FINISHED
        OrderStatus.FINISHED -> OrderStatus.DELIVERED
        OrderStatus.DELIVERED -> OrderStatus.CANCELLED
        OrderStatus.CANCELLED -> OrderStatus.QUOTATION
    }
}

fun FirestoreUser.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        customers = customers.toString().padStart(2, '0'),
        orders = orders.toString().padStart(2, '0'),
        userSince = getDateFromFirestoreTimestamp(createAt)
    )
}