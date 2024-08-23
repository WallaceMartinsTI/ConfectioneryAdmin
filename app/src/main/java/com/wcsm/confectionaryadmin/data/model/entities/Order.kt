package com.wcsm.confectionaryadmin.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = arrayOf("customer_id"),
            childColumns = arrayOf("customer_owner_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Order(
    @PrimaryKey
    @ColumnInfo(name = "order_id") val orderId: String,
    @ColumnInfo(name = "user_order_owner_id") val userOrderOwnerId: String,
    @ColumnInfo(name = "customer_owner_id") val customerOwnerId: String,
    val title: String,
    val description: String,
    val price: Double,
    val status: OrderStatus,
    @ColumnInfo(name = "order_date") val orderDate: Long,
    @ColumnInfo(name = "deliver_date") val deliverDate: Long
)
