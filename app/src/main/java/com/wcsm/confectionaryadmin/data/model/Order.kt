package com.wcsm.confectionaryadmin.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id") val orderId: Int = 0,
    @ColumnInfo(name = "customer_owner_id") val customerOwnerId: Int,
    val title: String,
    val description: String,
    val price: Double,
    val status: OrderStatus,
    @ColumnInfo(name = "order_date") val orderDate: Long,
    @ColumnInfo(name = "deliver_date") val deliverDate: Long
)
