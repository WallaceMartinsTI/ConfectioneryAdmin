package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.confectionaryadmin.data.model.CreateOrderState
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.ui.util.convertStringToDateMillis
import com.wcsm.confectionaryadmin.ui.util.getCurrentHourAndMinutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
)  : ViewModel() {
    private val _orderState = MutableStateFlow(CreateOrderState())
    val orderState = _orderState.asStateFlow()

    private val _newOrderCreated = MutableStateFlow(false)
    val newOrderCreated = _newOrderCreated.asStateFlow()

    fun updateCreateOrderState(newState: CreateOrderState) {
        _orderState.value = newState
    }

    fun createNewOrder() {
        _newOrderCreated.value = false

        if(isAllFieldValid()) {
            val currentHourAndMinute = getCurrentHourAndMinutes()
            val newOrder = Order(
                customerOwnerId = orderState.value.customer!!.customerId,
                title = orderState.value.orderName,
                description = orderState.value.orderDescription,
                price = 0.0,
                status = orderState.value.status,
                orderDate = convertStringToDateMillis(
                    dateString = "${orderState.value.orderDate} $currentHourAndMinute"
                ),
                deliverDate = convertStringToDateMillis(
                    dateString = "${orderState.value.deliverDate} 00:00"
                )
            )

            saveOrderToDatabase(order = newOrder)
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
        val orderDate = orderState.value.orderDate
        val deliverDate = orderState.value.deliverDate

        return if(!validateCustomer(customer)) {
            false
        } else if(!validateOrderName(orderName)) {
            false
        } else if(!validateOrderDescription(orderDescription)) {
            false
        } else if(!validateDateFields(orderDate, deliverDate)) {
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