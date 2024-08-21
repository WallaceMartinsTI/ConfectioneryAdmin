package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer

interface OrderRepository {

    suspend fun getOrdersWithCustomers(): List<OrderWithCustomer>

    suspend fun getOrderByCustomerOwner(customerOwnerId: Int): List<Order>

    suspend fun insertOrder(order: Order)

    suspend fun updateOrder(order: Order)

    suspend fun deleteOrder(order: Order)

    suspend fun sendOrdersToSincronize(orders: List<Order>): Task<Void>

    suspend fun getOrdersFromFirestore(): List<Order>

    suspend fun saveOrdersToLocalDatabase(orders: List<Order>)

}