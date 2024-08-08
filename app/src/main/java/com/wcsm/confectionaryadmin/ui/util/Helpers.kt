package com.wcsm.confectionaryadmin.ui.util

import android.os.Build
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentYear(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().year
    } else {
        Calendar.getInstance().get(Calendar.YEAR)
    }
}

fun getCurrentMonth(ptBr: Boolean = false): String {
    val locale = if(ptBr) Locale("pt", "BR") else Locale.getDefault()

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val currentMonth = LocalDate.now().month
        currentMonth.getDisplayName(TextStyle.FULL, locale)
    } else {
        val calendar = Calendar.getInstance()
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
        month ?: "Unknown"
    }
}

fun capitalizeFirstLetter(text: String): String {
    return text.replaceFirstChar { it.uppercase() }
}

fun getMonthFromStringToIndex(month: String, ptBr: Boolean = false): Int {
    val ptBrMonths = listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )
    val enUsMonths = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    return if(ptBr) {
        ptBrMonths.indexOf(month)
    } else {
        enUsMonths.indexOf(month)
    }
}

fun getStartAndEndOfMonth(month: Int, year: Int): Pair<Long, Long> {
    val calendar = Calendar.getInstance()

    // Set start of the month
    calendar.set(year, month, 1, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfMonth = calendar.timeInMillis

    // Set end of the month
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    val endOfMonth = calendar.timeInMillis

    return Pair(startOfMonth, endOfMonth)
}

fun convertStringToDateMillis(dateString: String): Long {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = dateFormat.parse(dateString)
    return date?.time ?: 1577847600000 // 01/01/2020 00:00
}

fun convertMillisToString(millis: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = Date(millis)
    return dateFormat.format(date)
}

fun getStatusFromString(status: String): OrderStatus {
    return when (status) {
        "Orçamento" -> OrderStatus.QUOTATION
        "Confirmado" -> OrderStatus.CONFIRMED
        "Em Produção" -> OrderStatus.IN_PRODUCTION
        "Finalizado" -> OrderStatus.FINISHED
        "Entregue" -> OrderStatus.DELIVERED
        else -> OrderStatus.CANCELLED
    }
}

fun getStringStatusFromStatus(status: OrderStatus): String {
    return when (status) {
        OrderStatus.QUOTATION -> "Orçamento"
        OrderStatus.CONFIRMED -> "Confirmado"
        OrderStatus.IN_PRODUCTION -> "Em Produção"
        OrderStatus.FINISHED -> "Finalizado"
        OrderStatus.DELIVERED -> "Entregue"
        else -> "Cancelado"
    }
}

fun getCurrentHourAndMinutes(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val currentTime = LocalTime.now()
        val currentHour = currentTime.hour
        val currentMinute = currentTime.minute
        "$currentHour:$currentMinute"
    } else {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        "$currentHour:$currentMinute"
    }
}



