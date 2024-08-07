package com.wcsm.confectionaryadmin.ui.util

import android.os.Build
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
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