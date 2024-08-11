package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.CreateCustomerState
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    private val _customerState = MutableStateFlow(CreateCustomerState())
    val customerState = _customerState.asStateFlow()

    private val _newCustomerCreated = MutableStateFlow(false)
    val newCustomerCreated = _newCustomerCreated.asStateFlow()

    fun updateCreateCustomerState(newState: CreateCustomerState) {
        _customerState.value = newState
    }

    fun createNewCustomer() {
        _newCustomerCreated.value = false

        val customerName = customerState.value.name
        if(validateCustomername(customerName)) {
            val newCustomer = Customer(
                name = customerState.value.name,
                email = customerState.value.email,
                phone = customerState.value.phone,
                gender = customerState.value.gender,
                dateOfBirth = customerState.value.dateOfBirth,
                address = customerState.value.address,
                notes = customerState.value.notes
            )
            saveCustomerToDatabase(customer = newCustomer)
        }
    }

    private fun saveCustomerToDatabase(customer: Customer) {
        viewModelScope.launch {
            try {
                customerRepository.insertCustomer(customer)
                _newCustomerCreated.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun validateCustomername(name: String): Boolean {
        val newState = _customerState.value.copy(
            nameErrorMessage = null
        )
        updateCreateCustomerState(newState)

        return if(name.isEmpty()) {
            updateCreateCustomerState(
                newState.copy(
                    nameErrorMessage = "Digite o nome do cliente."
                )
            )
            false
        } else {
            true
        }
    }
}