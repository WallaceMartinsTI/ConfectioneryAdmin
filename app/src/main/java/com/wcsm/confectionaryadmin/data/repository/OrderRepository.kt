package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.OrderWithCustomer

interface OrderRepository {

    suspend fun getOrdersWithCustomers(): List<OrderWithCustomer>

    suspend fun getOrderByCustomerOwner(customerOwnerId: Int): List<Order>

    suspend fun insertOrder(order: Order)

    suspend fun updateOrder(order: Order)

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