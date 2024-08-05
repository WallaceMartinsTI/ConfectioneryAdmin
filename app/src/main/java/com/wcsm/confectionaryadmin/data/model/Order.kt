package com.wcsm.confectionaryadmin.data.model

data class Order(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val status: OrderStatus,
    val orderDate: String,
    val deliverDate: String
)
