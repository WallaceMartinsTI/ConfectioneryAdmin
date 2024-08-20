package com.wcsm.confectionaryadmin.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customer_id") val customerId: Int = 0,
    @ColumnInfo(name = "user_customer_owner_id") val userCustomerOwnerId: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val gender: String?,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: String?,
    val address: String?,
    val notes: String?,
    @ColumnInfo(name = "orders_quantity") val ordersQuantity: Int,
    @ColumnInfo(name = "customer_since") val customerSince: Long
)