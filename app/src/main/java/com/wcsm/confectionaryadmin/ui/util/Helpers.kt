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