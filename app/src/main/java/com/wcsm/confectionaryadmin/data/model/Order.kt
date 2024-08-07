package com.wcsm.confectionaryadmin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String,
    val price: Double,
    val status: OrderStatus,
    val orderDate: Long,
    val deliverDate: Long
)
