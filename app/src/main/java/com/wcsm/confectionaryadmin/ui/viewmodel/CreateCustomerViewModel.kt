package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
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

    fun updateNewCustomerCreated(newStatus: Boolean) {
        _newCustomerCreated.value = newStatus
    }

    fun createNewCustomer() {
        val customerName = customerState.value.name
        if(validateCustomername(customerName)) {
            val formattedName = formatName(customerName)
            val newCustomer = Customer(
                name = formattedName,
                email = customerState.value.email.lowercase(),
                phone = customerState.value.phone,
                gender = customerState.value.gender,
                dateOfBirth = customerState.value.dateOfBirth,
                address = customerState.value.address,
                notes = customerState.value.notes
            )
            saveCustomerToDatabase(customer = newCustomer)
        }
    }

    private fun formatName(name: String): String {
        return name
            .split(" ")
            .joinToString(" ") {
                it.lowercase().replaceFirstChar {
                    char -> char.uppercase()
                }
            }
    }

    private fun saveCustomerToDatabase(customer: Customer) {
        viewModelScope.launch {
            try {
                customerRepository.insertCustomer(customer)
                Log.i("#-# TESTE #-#", "Inseriu customer no DB: $customer")
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