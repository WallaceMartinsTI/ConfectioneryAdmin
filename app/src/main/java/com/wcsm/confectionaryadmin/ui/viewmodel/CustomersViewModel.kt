package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.CustomerWithOrders
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    private val _customersWithOrders = MutableStateFlow<List<CustomerWithOrders>>(emptyList())
    val customersWithOrders = _customersWithOrders.asStateFlow()

    init {
        getAllCustomers()
    }

    private fun getAllCustomers() {
        viewModelScope.launch {
            try {
                val customersWithOrders = customerRepository.getCustomersWithOrders()
                _customersWithOrders.value = customersWithOrders
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}