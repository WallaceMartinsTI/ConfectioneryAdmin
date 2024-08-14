package com.wcsm.confectionaryadmin.ui.util

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.wcsm.confectionaryadmin.data.model.OrderStatus
import java.io.Serializable
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

fun getNextStatus(orderStatus: OrderStatus): OrderStatus {
    return when(orderStatus) {
        OrderStatus.QUOTATION -> OrderStatus.CONFIRMED
        OrderStatus.CONFIRMED -> OrderStatus.IN_PRODUCTION
        OrderStatus.IN_PRODUCTION -> OrderStatus.FINISHED
        OrderStatus.FINISHED -> OrderStatus.DELIVERED
        OrderStatus.DELIVERED -> OrderStatus.CANCELLED
        OrderStatus.CANCELLED -> OrderStatus.QUOTATION
    }
}

fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}