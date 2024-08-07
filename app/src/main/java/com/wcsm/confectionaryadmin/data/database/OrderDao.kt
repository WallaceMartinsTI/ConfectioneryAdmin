package com.wcsm.confectionaryadmin.data.database

import androidx.room.Query
import com.wcsm.confectionaryadmin.data.model.Order

interface OrderDao {

    @Query("SELECT * FROM orders WHERE orderDate BETWEEN :startOfMonth AND :endOfMonth")
    fun getOrdersByOrderDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

    @Query("SELECT * FROM orders WHERE deliverDate BETWEEN :startOfMonth AND :endOfMonth")
    fun getOrdersByDeliverDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

}