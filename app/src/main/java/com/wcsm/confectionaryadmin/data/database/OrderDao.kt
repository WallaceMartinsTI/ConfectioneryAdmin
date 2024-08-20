package com.wcsm.confectionaryadmin.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrder(order: Order)

    @Transaction
    @Query("SELECT * FROM orders WHERE user_order_owner_id = :userOwnerId")
    suspend fun getOrdersWithCustomers(userOwnerId: String): List<OrderWithCustomer>

    @Query("SELECT * FROM orders WHERE user_order_owner_id = :userOwnerId AND customer_owner_id = :customerOwnerId")
    suspend fun getOrderByCustomerOwner(userOwnerId: String, customerOwnerId: Int): List<Order>

    @Delete
    suspend fun deleteOrder(order: Order)

}