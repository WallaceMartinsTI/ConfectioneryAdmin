package com.wcsm.confectionaryadmin.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wcsm.confectionaryadmin.data.model.entities.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCustomer(customer: Customer)

    @Query("SELECT * FROM customers ORDER BY name")
    suspend fun getAllCustomers(): List<Customer>

    @Query("SELECT * FROM customers WHERE customer_id = :customerOwnerId")
    suspend fun getCustomerOwner(customerOwnerId: Int): Customer

    @Delete
    suspend fun deleteCustomer(customer: Customer)

}