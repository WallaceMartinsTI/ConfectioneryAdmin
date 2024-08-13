package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.Customer
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
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers = _customers.asStateFlow()

    private val _selectedCustomer = MutableStateFlow<Customer?>(null)
    val selectedCustomer = _selectedCustomer.asStateFlow()

    private val _isCustomerDeleted = MutableStateFlow(false)
    val isCustomerDeleted = _isCustomerDeleted.asStateFlow()

    init {
        getAllCustomers()
    }

    fun updateSelectedCustomer(customer: Customer?) {
        _selectedCustomer.value = customer
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                customerRepository.deleteCustomer(customer)
                getAllCustomers()
                _isCustomerDeleted.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _isCustomerDeleted.value = false
            }
        }
    }

    fun getAllCustomers() {
        viewModelScope.launch {
            try {
                val customers = customerRepository.getAllCustomers()
                _customers.value = customers
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}