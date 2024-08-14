package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.model.OrderWithCustomer
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _ordersWithCustomer = MutableStateFlow<List<OrderWithCustomer>>(emptyList())
    val ordersWithCustomer = _ordersWithCustomer.asStateFlow()

    private val _orderToChangeStatus = MutableStateFlow<Order?>(null)
    val orderToChangeStatus = _orderToChangeStatus.asStateFlow()

    private val _customerOrders = MutableStateFlow<List<Order>?>(null)
    val customerOrders = _customerOrders.asStateFlow()

    private val _filterResult = MutableStateFlow("")
    val filterResult = _filterResult.asStateFlow()

    init {
        getAllOrders()
    }

    fun updateOrderToChangeStatus(order: Order?) {
        _orderToChangeStatus.value = order
    }

    fun updateFilterResult(newResult: String) { // "" or Outubro/2024
        _filterResult.value = newResult
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            try {
                orderRepository.updateOrder(order)
                getAllOrders()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

    fun getOrdersByCustomer(customerOwnerId: Int) {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getOrderByCustomerOwner(customerOwnerId)
                _customerOrders.value = orders.reversed()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllOrders() {
        viewModelScope.launch {
            try {
                val ordersWithCustomer = orderRepository.getOrdersWithCustomers()
                _ordersWithCustomer.value = ordersWithCustomer.reversed()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}