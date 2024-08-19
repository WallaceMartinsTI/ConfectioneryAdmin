package com.wcsm.confectionaryadmin.data.model.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order

data class OrderWithCustomer(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "customer_owner_id",
        entityColumn = "customer_id"
    )
    val customer: Customer
)
