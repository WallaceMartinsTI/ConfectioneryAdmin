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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: List<Customer>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCustomer(customer: Customer)

    @Query("SELECT * FROM customers WHERE user_customer_owner_id = :userOwnerId ORDER BY name")
    suspend fun getAllCustomers(userOwnerId: String): List<Customer>

    @Delete
    suspend fun deleteCustomer(customer: Customer)

}