package com.wcsm.confectionaryadmin.data.model

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Relation
import com.google.gson.Gson

data class CustomerWithOrders(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerOwnerId"
    )
    val orders: List<Order>
)
