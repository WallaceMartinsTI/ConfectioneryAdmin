package com.wcsm.confectionaryadmin.data.model

data class Customer(
    val id: Int,
    val name: String,
    val email: String?,
    val phone: String?,
    val gender: String?,
    val dateOfBirth: String?,
    val address: String?,
    val notes: String?,
    val orders: List<Order>
)
