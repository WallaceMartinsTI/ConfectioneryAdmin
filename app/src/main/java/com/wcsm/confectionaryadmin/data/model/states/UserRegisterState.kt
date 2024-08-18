package com.wcsm.confectionaryadmin.data.model.states

data class UserRegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameErrorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val confirmPasswordErrorMessage: String? = null,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false
)
