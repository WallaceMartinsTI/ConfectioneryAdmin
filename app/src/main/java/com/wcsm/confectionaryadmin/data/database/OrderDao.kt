package com.wcsm.confectionaryadmin.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.wcsm.confectionaryadmin.data.model.Order

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM orders WHERE order_date BETWEEN :startOfMonth AND :endOfMonth")
    suspend fun getOrdersByOrderDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

    @Query("SELECT * FROM orders WHERE deliver_date BETWEEN :startOfMonth AND :endOfMonth")
    suspend fun getOrdersByDeliverDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

}