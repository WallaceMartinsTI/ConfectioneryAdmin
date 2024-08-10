package com.wcsm.confectionaryadmin.data.model

data class CreateOrderState(
    val customer: Customer? = null,
    val orderName: String = "",
    val orderDescription: String = "",
    val orderDate: String = "",
    val deliverDate: String = "",
    val status: OrderStatus = OrderStatus.QUOTATION,
    val customerErrorMessage: String? = null,
    val orderNameErrorMessage: String? = null,
    val orderDescriptionErrorMessage: String? = null,
    val orderDateErrorMessage: String? = null,
    val deliverDateErrorMessage: String? = null
)
