package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _ordersFromCloud = MutableStateFlow<List<Order>>(emptyList())
    private val _customersFromCloud = MutableStateFlow<List<Customer>>(emptyList())

    private val _isSyncLoading = MutableStateFlow(false)
    val isSyncLoading = _isSyncLoading.asStateFlow()

    private val _isSyncSuccess = MutableStateFlow(false)
    val isSyncSuccess = _isSyncSuccess.asStateFlow()

    init {
        Log.i("#-# TESTE #-#", "InfoViewModel INIT")
    }

    fun fetchAllUserData() {
        _isSyncSuccess.value = false
        _isSyncLoading.value = true
        viewModelScope.launch {
            try {
                fetchCustomersFromFirestore()

                fetchOrdersFromFirestore()

                Log.i("#-# SYNC #-#", "All user data fetched successfully!")
                Log.i("#-# SYNC #-#", "Now, trying to save in local Room database...")
                saveFirestoreDataToRoom()
            } catch (e: Exception) {
                Log.e("#-# SYNC #-#", "Error fetching all user data.", e)
            }
        }
    }

    private suspend fun saveFirestoreDataToRoom() {
        try {
            val customers = _customersFromCloud.value
            val orders = _ordersFromCloud.value
            customerRepository.saveCustomersToLocalDatabase(customers)
            orderRepository.saveOrdersToLocalDatabase(orders)
            Log.i("#-# SYNC #-#", "firestore data saved in room with success!")
            _isSyncSuccess.value = true
        } catch (e: Exception) {
            Log.e("#-# SYNC #-#", "Error saving firestore data in room.", e)
        }
        _isSyncLoading.value = false
    }

    private suspend fun fetchOrdersFromFirestore() {
        try {
            val orders = orderRepository.getOrdersFromFirestore()
            _ordersFromCloud.value = orders
            Log.i("#-# SYNC #-#", "fetchOrdersFromFirestore success!")
        } catch (e: Exception) {
            Log.e("#-# SYNC #-#", "Error in fetchOrdersFromFirestore function.", e)
        }
    }

    private suspend fun fetchCustomersFromFirestore() {
        try {
            val customers = customerRepository.getCustomersFromFirestore()
            _customersFromCloud.value = customers
            Log.i("#-# SYNC #-#", "fetchCustomersFromFirestore success!")
        } catch (e: Exception) {
            Log.e("#-# SYNC #-#", "Erro in fetchCustomersFromFirestore function.", e)
        }
    }
}