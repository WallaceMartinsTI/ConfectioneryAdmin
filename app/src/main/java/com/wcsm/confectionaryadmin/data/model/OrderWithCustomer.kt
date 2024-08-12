package com.wcsm.confectionaryadmin.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithCustomer(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "customerOwnerId",
        entityColumn = "customerId"
    )
    val customer: Customer
)
