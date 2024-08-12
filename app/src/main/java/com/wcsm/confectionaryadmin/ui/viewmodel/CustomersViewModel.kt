package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.Customer
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

    private val _selectedCustomer = MutableStateFlow<CustomerWithOrders?>(null)
    val selectedCustomer = _selectedCustomer.asStateFlow()

    init {
        getAllCustomers()
    }

    fun updateSelectedCustomer(customerWithOrders: CustomerWithOrders) {
        _selectedCustomer.value = customerWithOrders
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                customerRepository.deleteCustomer(customer)
                getAllCustomers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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