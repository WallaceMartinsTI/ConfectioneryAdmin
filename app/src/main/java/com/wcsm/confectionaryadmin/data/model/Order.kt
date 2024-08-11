package com.wcsm.confectionaryadmin.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val customerOwnerId: Int,
    val title: String,
    val description: String,
    val price: Double,
    val status: OrderStatus,
    @ColumnInfo(name = "order_date") val orderDate: Long,
    @ColumnInfo(name = "deliver_date") val deliverDate: Long
)
