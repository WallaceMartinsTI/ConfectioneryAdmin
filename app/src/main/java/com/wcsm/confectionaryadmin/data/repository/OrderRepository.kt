package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer

interface OrderRepository {

    suspend fun getOrdersWithCustomers(userOwnerId: String): List<OrderWithCustomer>

    suspend fun getOrderByCustomerOwner(userOwnerId: String, customerOwnerId: String): List<Order>

    suspend fun insertOrder(order: Order)

    suspend fun updateOrder(order: Order)

    suspend fun deleteOrder(order: Order)

    suspend fun getUserOrdersQuantity(userOwnerId: String): Int

    suspend fun sendOrdersToSincronize(userOwnerId: String, orders: List<Order>): Task<Void>

    suspend fun getOrdersFromFirestore(userOwnerId: String): List<Order>

    suspend fun saveOrdersToLocalDatabase(orders: List<Order>)

    suspend fun deleteAllUserOrdersRoom(userOwnerId: String)

    suspend fun deleteAllUserOrdersFirestore(userOwnerId: String): Task<Void>

}