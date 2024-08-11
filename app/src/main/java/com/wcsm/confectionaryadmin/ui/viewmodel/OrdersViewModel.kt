package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    private val _filterResult = MutableStateFlow("")
    val filterResult = _filterResult.asStateFlow()

    init {
        getAllOrders()
    }

    fun updateFilterResult(newResult: String) { // "" or Outubro/2024
        _filterResult.value = newResult
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            try {
                orderRepository.deleteOrder(order)
                getAllOrders()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getAllOrders() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getAllOrders()
                _orders.value = orders.reversed()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}