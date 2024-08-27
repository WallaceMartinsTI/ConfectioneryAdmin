package com.wcsm.confectionaryadmin.ui.util

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.google.firebase.Timestamp
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus
import java.text.SimpleDateFormat
import java.time.LocalDate
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

fun convertDateMonthYearStringToLong(dateString: String): Long {
    val splitedDate = dateString.split("/")
    val monthIndex = when (splitedDate[0]) {
        "Janeiro" -> 1
        "Fevereiro" -> 2
        "Março" -> 3
        "Abril" -> 4
        "Maio" -> 5
        "Junho" -> 6
        "Julho" -> 7
        "Agosto" -> 8
        "Setembro" -> 9
        "Outubro" -> 10
        "Novembro" -> 11
        else -> 12
    }
    val formattedMonth = if (monthIndex < 10) "0$monthIndex" else monthIndex
    val formattedDate = "01/$formattedMonth/${splitedDate[1]} 00:00"
    return convertStringToDateMillis(formattedDate)
}

fun convertMillisToString(millis: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = Date(millis)
    return dateFormat.format(date)
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

fun getCurrentDateTimeMillis(): Long {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val currentDateTime = Date()
    val formattedDate = dateFormat.format(currentDateTime)
    val date = dateFormat.parse(formattedDate)
    return date?.time ?: 1577847600000
}

fun formatNameCapitalizeFirstChar(name: String): String {
    return name
        .split(" ")
        .joinToString(" ") {
            it.lowercase().replaceFirstChar {
                char -> char.uppercase()
            }
        }
}

fun getYearAndMonthFromTimeInMillis(timeInMillis: Long): Pair<Int, Int> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1

    return Pair(year, month)
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

fun getDateFromFirestoreTimestamp(timestamp: Timestamp): String {
    val date: Date = timestamp.toDate()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}