package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun getOrdersWithCustomers(): List<OrderWithCustomer> {
        return orderDao.getOrdersWithCustomers()
    }

    override suspend fun getOrderByCustomerOwner(customerOwnerId: Int): List<Order> {
        return orderDao.getOrderByCustomerOwner(customerOwnerId)
    }


    override suspend fun insertOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    override suspend fun updateOrder(order: Order) {
        return orderDao.updateOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        orderDao.deleteOrder(order)
    }

}