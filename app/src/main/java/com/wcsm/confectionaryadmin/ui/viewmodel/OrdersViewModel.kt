package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer
import com.wcsm.confectionaryadmin.data.model.states.OrderSyncState
import com.wcsm.confectionaryadmin.data.model.types.FilterType
import com.wcsm.confectionaryadmin.data.model.types.OrderDateType
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import com.wcsm.confectionaryadmin.ui.util.Constants.ROOM_TAG
import com.wcsm.confectionaryadmin.ui.util.Constants.SYNC_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _ordersWithCustomer = MutableStateFlow<List<OrderWithCustomer>>(emptyList())
    val ordersWithCustomer = _ordersWithCustomer.asStateFlow()

    private val _orderToChangeStatus = MutableStateFlow<Order?>(null)
    val orderToChangeStatus = _orderToChangeStatus.asStateFlow()

    private val _orderToBeEditted = MutableStateFlow<OrderWithCustomer?>(null)
    val orderToBeEditted = _orderToBeEditted.asStateFlow()

    private val _customerOrders = MutableStateFlow<List<Order>?>(null)
    val customerOrders = _customerOrders.asStateFlow()

    private val _filterType = MutableStateFlow<FilterType?>(null)
    val filterType = _filterType.asStateFlow()

    private val _orderSyncState = MutableStateFlow(OrderSyncState())
    val orderSyncState = _orderSyncState.asStateFlow()

    private val _orderDateType = MutableStateFlow(OrderDateType.ORDER_DATE)
    val orderDateType = _orderDateType.asStateFlow()

    private val _filterResult = MutableStateFlow("")
    val filterResult = _filterResult.asStateFlow()

    private var _currentUser: FirebaseUser? = null

    init {
        viewModelScope.launch {
            _currentUser = userRepository.getCurrentUser()
            getAllOrders()
        }
    }

    fun updateOrderToChangeStatus(order: Order?) {
        _orderToChangeStatus.value = order
    }

    fun updateFilterType(filterType: FilterType?) {
        _filterType.value = filterType
    }

    fun updateOrderDateType(orderDateType: OrderDateType) {
        _orderDateType.value = orderDateType
    }

    fun updateFilterResult(newResult: String) {
        _filterResult.value = newResult
    }

    fun updateOrderToBeEditted(orderWithCustomer: OrderWithCustomer?) {
        _orderToBeEditted.value = orderWithCustomer
    }

    fun updateOrderSyncState(newState: OrderSyncState) {
        _orderSyncState.value = newState
    }

    fun updateOrderStatus(order: Order) {
        viewModelScope.launch {
            try {
                orderRepository.updateOrder(order)
                updateOrderSyncState(
                    orderSyncState.value.copy(
                        isSynchronized = false
                    )
                )
                Log.i(ROOM_TAG, "Order status changed successfully!")
                getAllOrders()
            } catch (e: Exception) {
                Log.e(ROOM_TAG, "Error changing order status.", e)
            }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            try {
                orderRepository.deleteOrder(order)
                updateOrderSyncState(
                    orderSyncState.value.copy(
                        isSynchronized = false
                    )
                )
                Log.i(ROOM_TAG, "Order deleted successfully!")
                getAllOrders()
            } catch (e: Exception) {
                Log.e(ROOM_TAG, "Error deleting order", e)
            }
        }
    }

    fun getOrdersByCustomer(customerOwnerId: String) {
        if(_currentUser != null) {
            viewModelScope.launch {
                try {
                    val orders = orderRepository.getOrderByCustomerOwner(
                        userOwnerId = _currentUser!!.uid,
                        customerOwnerId = customerOwnerId
                    )
                    Log.i(ROOM_TAG, "Get orders by customer successfully!")
                    _customerOrders.value = orders.reversed()
                } catch (e: Exception) {
                    Log.e(ROOM_TAG, "Error getting orders by customer.", e)
                }
            }
        }
    }

    fun getAllOrders() {
        if(_currentUser != null) {
            viewModelScope.launch {
                try {
                    val ordersWithCustomer = orderRepository.getOrdersWithCustomers(
                        userOwnerId = _currentUser!!.uid
                    )
                    Log.i(ROOM_TAG, "Get all orders successfully!")
                    _ordersWithCustomer.value = ordersWithCustomer.reversed()
                } catch (e: Exception) {
                    Log.e(ROOM_TAG, "Error getting all orders.", e)
                }
            }
        }
    }

    fun sendOrdersToSync() {
        val newState = OrderSyncState(
            isSynchronized = false,
            syncError = false
        )
        updateOrderSyncState(newState)

        if(_currentUser != null) {
            viewModelScope.launch {
                val orders = ordersWithCustomer.value.map {
                    it.order
                }
                orderRepository.sendOrdersToSync(
                    userOwnerId = _currentUser!!.uid,
                    orders = orders
                )
                    .addOnSuccessListener {
                        updateOrderSyncState(
                            newState.copy(
                                isSynchronized = true
                            )
                        )
                        Log.i(SYNC_TAG, "Orders sent to sync successfully!")
                    }
                    .addOnFailureListener { e ->
                        updateOrderSyncState(
                            newState.copy(
                                syncError = true
                            )
                        )
                        Log.e(SYNC_TAG, "Error sending orders to sync.", e)
                    }
            }
        }
    }
}