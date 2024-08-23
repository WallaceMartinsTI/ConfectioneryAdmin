package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.states.CustomerSyncState
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import com.wcsm.confectionaryadmin.ui.util.showToastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers = _customers.asStateFlow()

    private val _selectedCustomer = MutableStateFlow<Customer?>(null)
    val selectedCustomer = _selectedCustomer.asStateFlow()

    private val _customerSyncState = MutableStateFlow(CustomerSyncState())
    val customerSyncState = _customerSyncState.asStateFlow()

    private val _isCustomerDeleted = MutableStateFlow(false)
    val isCustomerDeleted = _isCustomerDeleted.asStateFlow()

    private var _currentUser: FirebaseUser? = null

    init {
        viewModelScope.launch {
            _currentUser = userRepository.getCurrentUser()
            getAllCustomers()
        }
    }

    fun updateSelectedCustomer(customer: Customer?) {
        _selectedCustomer.value = customer
    }

    fun updateCustomerSyncState(newState: CustomerSyncState) {
        _customerSyncState.value = newState
    }

    fun updateCustomerDeletes(status: Boolean) {
        _isCustomerDeleted.value = status
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
        if(_currentUser != null) {
            viewModelScope.launch {
                try {
                    val customers = customerRepository.getAllCustomers(_currentUser!!.uid)
                    _customers.value = customers
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun sendCustomersToSincronize() {
        val newState = CustomerSyncState(
            isSincronized = false,
            syncError = false
        )
        updateCustomerSyncState(newState)

        if(_currentUser != null) {
            viewModelScope.launch {
                customerRepository.sendCustomersToSincronize(
                    userOwnerId = _currentUser!!.uid,
                    customers = customers.value
                )
                    .addOnSuccessListener {
                        updateCustomerSyncState(
                            newState.copy(
                                isSincronized = true
                            )
                        )
                    }
                    .addOnFailureListener {
                        updateCustomerSyncState(
                            newState.copy(
                                syncError = true
                            )
                        )
                    }
            }
        }
    }
}