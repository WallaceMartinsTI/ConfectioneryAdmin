package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wcsm.confectionaryadmin.data.model.states.CreateCustomerState
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import com.wcsm.confectionaryadmin.ui.util.formatNameCapitalizeFirstChar
import com.wcsm.confectionaryadmin.ui.util.getCurrentDateTimeMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _customerState = MutableStateFlow(
        CreateCustomerState(customerSince = "")
    )
    val customerState = _customerState.asStateFlow()

    private val _newCustomerCreated = MutableStateFlow(false)
    val newCustomerCreated = _newCustomerCreated.asStateFlow()

    private val _customerUpdated = MutableStateFlow(false)
    val customerUpdated = _customerUpdated.asStateFlow()

    fun updateCreateCustomerState(newState: CreateCustomerState) {
        _customerState.value = newState
    }

    fun updateNewCustomerCreated(newStatus: Boolean) {
        _newCustomerCreated.value = newStatus
    }

    fun updateCustomerUpdated(newStatus: Boolean) {
        _customerUpdated.value = newStatus
    }

    fun saveCustomer(
        isUpdateCustomer: Boolean = false
    ) {
        val currentUser = auth.currentUser
        if(currentUser == null) {
            Log.e("ERROR", "User unidentified")
            return
        }
        val customerName = customerState.value.name
        if(validateCustomername(customerName)) {
            val formattedName = formatNameCapitalizeFirstChar(customerName.trim())
            val customer = Customer(
                userCustomerOwnerId = currentUser.uid,
                customerId = customerState.value.customerId ?: 0,
                name = formattedName,
                email = customerState.value.email.trim().lowercase(),
                phone = customerState.value.phone,
                gender = customerState.value.gender,
                dateOfBirth = customerState.value.dateOfBirth,
                address = customerState.value.address,
                notes = customerState.value.notes,
                ordersQuantity = 0,
                customerSince = getCurrentDateTimeMillis()
            )

            if(isUpdateCustomer) {
                _customerUpdated.value = false
                updateUserToDatabase(customer = customer)
            } else {
                saveUserToDatabase(customer = customer)
            }
        }
    }

    private fun updateUserToDatabase(customer: Customer) {
        viewModelScope.launch {
            try {
                customerRepository.updateCustomer(customer)
                _customerUpdated.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveUserToDatabase(customer: Customer) {
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