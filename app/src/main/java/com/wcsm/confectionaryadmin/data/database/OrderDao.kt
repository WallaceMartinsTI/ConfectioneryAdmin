package com.wcsm.confectionaryadmin.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.model.OrderWithCustomer

@Dao
interface OrderDao {

    @Transaction
    @Query("SELECT * FROM orders")
    suspend fun getOrdersWithCustomers(): List<OrderWithCustomer>

    @Query("SELECT * FROM orders WHERE customer_owner_id = :customerOwnerId")
    suspend fun getOrderByCustomerOwner(customerOwnerId: Int): List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM orders WHERE order_date BETWEEN :startOfMonth AND :endOfMonth")
    suspend fun getOrdersByOrderDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

    @Query("SELECT * FROM orders WHERE deliver_date BETWEEN :startOfMonth AND :endOfMonth")
    suspend fun getOrdersByDeliverDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

}