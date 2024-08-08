package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.model.Order
import kotlinx.coroutines.flow.StateFlow

interface OrderRepository {

    suspend fun getAllOrders(): List<Order>

    suspend fun insertOrder(order: Order)

    suspend fun deleteOrder(order: Order)

    suspend fun getOrdersByOrderDateFilteredByMonthAndYear(
        startOfMonth: Long,
        endOfMonth: Long
    ): List<Order>

    suspend fun getOrdersByDeliverDateFilteredByMonthAndYear(
        startOfMonth: Long,
        endOfMonth: Long
    ): List<Order>

}