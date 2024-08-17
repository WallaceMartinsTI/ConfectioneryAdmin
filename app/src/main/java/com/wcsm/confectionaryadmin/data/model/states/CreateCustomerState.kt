package com.wcsm.confectionaryadmin.data.model.states

data class CreateCustomerState(
    val customerId: Int? = null,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val gender: String = "",
    val dateOfBirth: String = "",
    val address: String = "",
    val notes: String = "",
    val nameErrorMessage: String? = null
)