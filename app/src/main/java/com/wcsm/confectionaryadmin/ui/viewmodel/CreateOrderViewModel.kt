package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
)  : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders

    init {
        Log.i("#-# TESTE #-#", "VIEWMODEL - init")
        getAllOrders()
    }

    private fun getAllOrders() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getAllOrders()
                _orders.value = orders
            } catch (e: Exception) {
                Log.i("#-# TESTE #-#", "VIEWMODEL - Caiu no CATCH")
                e.printStackTrace()
            }
        }
    }

    fun saveOrder(order: Order) {
        Log.i("#-# TESTE #-#", "VIEWMODEL - Order recebida: $order")
        viewModelScope.launch {
            try {
                orderRepository.insertOrder(order)
            } catch (e: Exception) {
                Log.i("#-# TESTE #-#", "VIEWMODEL - Caiu no CATCH")
                e.printStackTrace()
            }
        }
    }

}