package com.wcsm.confectionaryadmin.data.model.entities

import com.google.firebase.Timestamp

data class FirestoreUser(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val customers: Int = 0,
    val orders: Int = 0,
    val createAt: Timestamp = Timestamp.now()
)