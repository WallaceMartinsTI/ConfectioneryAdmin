package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.data.repository.OrderRepositoryImpl
import com.wcsm.confectionaryadmin.ui.util.getMonthFromStringToIndex
import com.wcsm.confectionaryadmin.ui.util.getStartAndEndOfMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class OrdersViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    private val _filterResult = MutableStateFlow("")
    val filterResult = _filterResult.asStateFlow()

    fun updateFilterResult(newResult: String) { // "" or Outubro/2024
        _filterResult.value = newResult
    }

    /*fun getOrdersFilteredByMonthAndYear(
        month: String,
        year: Int,
        isByDeliverDate: Boolean = false
    ) {
        val indexMonth = getMonthFromStringToIndex(month)
        val (startOfMonth, endOfMonth) = getStartAndEndOfMonth(month = indexMonth, year = year)

        if(isByDeliverDate) {
            viewModelScope.launch {
                orderRepository.getOrdersByDeliverDateFilteredByMonthAndYear(
                    startOfMonth = startOfMonth,
                    endOfMonth = endOfMonth
                )
            }
        } else {
            viewModelScope.launch {
                orderRepository.getOrdersByOrderDateFilteredByMonthAndYear(
                    startOfMonth = startOfMonth,
                    endOfMonth = endOfMonth
                )
            }
        }
    }*/
}