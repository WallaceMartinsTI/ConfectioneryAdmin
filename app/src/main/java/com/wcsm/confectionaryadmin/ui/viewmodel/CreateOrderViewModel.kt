package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.wcsm.confectionaryadmin.data.model.states.CreateOrderState
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.ui.util.convertStringToDateMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val auth: FirebaseAuth
)  : ViewModel() {
    private val _orderState = MutableStateFlow(CreateOrderState())
    val orderState = _orderState.asStateFlow()

    private val _newOrderCreated = MutableStateFlow(false)
    val newOrderCreated = _newOrderCreated.asStateFlow()

    private val _orderUpdated = MutableStateFlow(false)
    val orderUpdated = _orderUpdated.asStateFlow()

    fun updateCreateOrderState(newState: CreateOrderState) {
        _orderState.value = newState
    }

    fun updateOrderUpdated(newStatus: Boolean) {
        _orderUpdated.value = newStatus
    }

    fun createNewOrder(
        isUpdateOrder: Boolean = false
    ) {
        val currentUser = auth.currentUser
        if(currentUser == null) {
            Log.e("ERROR", "User unidentified")
            return
        }

        if(isAllFieldValid()) {
            val newOrderId = "${currentUser.uid}-${UUID.randomUUID()}"
            val order = Order(
                userOrderOwnerId = currentUser.uid,
                orderId = orderState.value.orderId ?: newOrderId,
                customerOwnerId = orderState.value.customer!!.customerId,
                title = orderState.value.orderName,
                description = orderState.value.orderDescription,
                price = orderState.value.price.toDouble(),
                status = orderState.value.status,
                orderDate = convertStringToDateMillis(
                    dateString = orderState.value.orderDate
                ),
                deliverDate = convertStringToDateMillis(
                    dateString = orderState.value.deliverDate
                )
            )

            if(isUpdateOrder) {
                _orderUpdated.value = false
                updateOrderToDatabase(order = order)
            } else {
                saveOrderToDatabase(order = order)
            }
        }
    }

    private fun updateOrderToDatabase(order: Order) {
        viewModelScope.launch {
            try {
                orderRepository.updateOrder(order)
                _orderUpdated.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveOrderToDatabase(order: Order) {
        viewModelScope.launch {
            try {
                orderRepository.insertOrder(order)
                _newOrderCreated.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isAllFieldValid(): Boolean {
        val customer = orderState.value.customer
        val orderName = orderState.value.orderName
        val orderDescription = orderState.value.orderDescription
        val price = orderState.value.price
        val orderDate = orderState.value.orderDate
        val deliverDate = orderState.value.deliverDate

        return if(!validateCustomer(customer)) {
            false
        } else if(!validateOrderName(orderName)) {
            false
        } else if(!validateOrderDescription(orderDescription)) {
            false
        } else if(!validateValue(price)) {
            false
        }
        else if(!validateDateFields(orderDate, deliverDate)) {
            false
        }
        else {
            true
        }
    }

    private fun validateDateFields(orderDate: String, deliverDate: String): Boolean {
        val newState = _orderState.value.copy(
            orderDateErrorMessage = null,
            deliverDateErrorMessage = null
        )
        updateCreateOrderState(newState)

        return if(orderDate.isEmpty()) {
            updateCreateOrderState(
                newState.copy(
                    orderDateErrorMessage = "Selecione a data do pedido."
                )
            )
            false
        } else if(deliverDate.isEmpty()) {
            updateCreateOrderState(
                newState.copy(
                    deliverDateErrorMessage = "Selecione a data da entrega."
                )
            )
            false
        } else {
            true
        }
    }

    private fun validateValue(value: String): Boolean {
        val newState = _orderState.value.copy(
            priceErrorMessage = null,
        )
        updateCreateOrderState(newState)

        if(value.length > 9) { // Limited of: 0100000 -> R$100.000,00
            updateCreateOrderState(
                orderState.value.copy(
                    priceErrorMessage = "Valor muito alto. Limite: R$100.000,00."
                )
            )
            return false
        }

        var result: Double? = null
        val decimals = listOf("01", "02", "03", "04", "05", "06", "07", "08", "09")

        if(value == "0") {
            result = 0.0
            updateCreateOrderState(
                orderState.value.copy(
                    price = result.toString()
                )
            )
            return true
        } else if(value in decimals) {
            result = "0.$value".toDouble()
        }

        if(value.isNotBlank() && value.toDoubleOrNull() != null) {
            try {
                val numericValue = value.replace(Regex("[^\\d]"), "")

                val integerPart = numericValue.substring(0, numericValue.length - 2).toInt()
                val decimalPart = numericValue.substring(numericValue.length - 2).toInt()

                result = integerPart + decimalPart.toDouble() / 100
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return if(result == null) {
            updateCreateOrderState(
                orderState.value.copy(
                    priceErrorMessage = "Valor inválido."
                )
            )
            false
        } else {
            updateCreateOrderState(
                orderState.value.copy(
                    price = result.toString()
                )
            )
            true
        }
    }

    private fun validateOrderDescription(orderDescription: String): Boolean {
        val newState = _orderState.value.copy(
            orderDescriptionErrorMessage = null
        )
        updateCreateOrderState(newState)

        return if(orderDescription.isEmpty()) {
            updateCreateOrderState(
                newState.copy(
                    orderDescriptionErrorMessage = "Digite uma descrição."
                )
            )
            false
        } else if(orderDescription.length < 3 || orderDescription.length > 100) {
            updateCreateOrderState(
                newState.copy(
                    orderDescriptionErrorMessage = "Digite uma descrição entre 3 e 100 caracteres."
                )
            )
            false
        } else {
            true
        }
    }

    private fun validateOrderName(orderName: String): Boolean {
        val newState = _orderState.value.copy(
            orderNameErrorMessage = null
        )
        updateCreateOrderState(newState)

        return if(orderName.isEmpty()) {
            updateCreateOrderState(
                newState.copy(
                    orderNameErrorMessage = "Digite o nome do pedido."
                )
            )
            false
        } else if(orderName.length < 3 || orderName.length > 40) {
            updateCreateOrderState(
                newState.copy(
                    orderNameErrorMessage = "Digite um nome entre 3 e 40 caracteres."
                )
            )
            false
        } else {
            true
        }
    }

    private fun validateCustomer(customer: Customer?): Boolean {
        val newState = _orderState.value.copy(
            customerErrorMessage = null
        )
        updateCreateOrderState(newState)

        return if(customer == null) {
            updateCreateOrderState(
                newState.copy(
                    customerErrorMessage = "Selecione um cliente."
                )
            )
            false
        } else {
            true
        }
    }
}