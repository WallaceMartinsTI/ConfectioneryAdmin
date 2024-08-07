package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.model.Order

interface OrderRepository {

    suspend fun getOrdersByOrderDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

    suspend fun getOrdersByDeliverDateFilteredByMonthAndYear(startOfMonth: Long, endOfMonth: Long): List<Order>

}