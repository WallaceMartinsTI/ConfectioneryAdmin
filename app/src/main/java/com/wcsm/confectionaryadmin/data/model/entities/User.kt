package com.wcsm.confectionaryadmin.data.model.entities

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val orderWithCustomer: List<OrderWithCustomer>
)
