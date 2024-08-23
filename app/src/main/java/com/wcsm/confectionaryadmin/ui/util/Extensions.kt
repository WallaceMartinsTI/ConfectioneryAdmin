package com.wcsm.confectionaryadmin.ui.util

import androidx.compose.ui.graphics.Color
import com.wcsm.confectionaryadmin.data.model.entities.FirestoreUser
import com.wcsm.confectionaryadmin.data.model.entities.User
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus
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
        OrderStatus.QUOTATION -> QuotationStatus
        OrderStatus.CONFIRMED -> ConfirmedStatus
        OrderStatus.IN_PRODUCTION -> InProductionStatus
        OrderStatus.FINISHED -> FinishedStatus
        OrderStatus.DELIVERED -> DeliveredStatus
        OrderStatus.CANCELLED -> CancelledStatus
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