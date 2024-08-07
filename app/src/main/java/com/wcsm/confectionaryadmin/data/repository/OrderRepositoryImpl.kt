package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.model.Order

class OrderRepositoryImpl(private val orderDao: OrderDao) : OrderRepository {

    override suspend fun getOrdersByOrderDateFilteredByMonthAndYear(
        startOfMonth: Long,
        endOfMonth: Long
    ): List<Order> {
        return orderDao.getOrdersByOrderDateFilteredByMonthAndYear(startOfMonth, endOfMonth)
    }

    override suspend fun getOrdersByDeliverDateFilteredByMonthAndYear(
        startOfMonth: Long,
        endOfMonth: Long
    ): List<Order> {
        return orderDao.getOrdersByDeliverDateFilteredByMonthAndYear(startOfMonth, endOfMonth)
    }

}