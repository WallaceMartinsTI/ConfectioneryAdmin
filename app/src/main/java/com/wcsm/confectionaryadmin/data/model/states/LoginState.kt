package com.wcsm.confectionaryadmin.data.model.states

data class LoginState(
    val email: String = "",
    val senha: String = "",
    val emailErrorMessage: String? = null,
    val senhaErrorMessage: String? = null
)