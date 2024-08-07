package com.wcsm.confectionaryadmin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "customer_id") val customerId: Int,
    val title: String,
    val description: String,
    val price: Double,
    val status: OrderStatus,
    @ColumnInfo(name = "order_date") val orderDate: Long,
    @ColumnInfo(name = "deliver_date") val deliverDate: Long
)
