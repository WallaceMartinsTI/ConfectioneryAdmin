package com.wcsm.confectionaryadmin.data.model.entities

import com.google.firebase.Timestamp

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val customers: String = "",
    val orders: String = "",
    val userSince: String = ""
)