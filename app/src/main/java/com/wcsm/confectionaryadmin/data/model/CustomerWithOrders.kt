package com.wcsm.confectionaryadmin.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithOrders(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerOwnerId"
    )
    val orders: List<Order>
)
