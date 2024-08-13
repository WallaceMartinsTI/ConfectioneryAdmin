package com.wcsm.confectionaryadmin.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.wcsm.confectionaryadmin.data.model.Customer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customers ORDER BY name")
    suspend fun getAllCustomers(): List<Customer>

    @Query("SELECT * FROM customers WHERE customer_id = :customerOwnerId")
    suspend fun getCustomerOwner(customerOwnerId: Int): Customer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

}