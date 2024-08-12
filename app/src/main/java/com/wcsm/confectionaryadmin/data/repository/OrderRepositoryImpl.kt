package com.wcsm.confectionaryadmin.data.repository

import android.util.Log
import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.CustomerWithOrders
import com.wcsm.confectionaryadmin.data.model.Order
import com.wcsm.confectionaryadmin.data.model.OrderWithCustomer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun getAllOrdersWithCustomer(): List<OrderWithCustomer> {
        return orderDao.getAllOrdersWithCustomer()
    }

    override suspend fun insertOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        orderDao.deleteOrder(order)
    }

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