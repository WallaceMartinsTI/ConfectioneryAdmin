package com.wcsm.confectionaryadmin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customer_id") val customerId: Int = 0,
    val name: String,
    val email: String?,
    val phone: String?,
    val gender: String?,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: String?,
    val address: String?,
    val notes: String?
)