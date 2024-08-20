package com.wcsm.confectionaryadmin.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : OrderRepository {
    private val currentUser = auth.currentUser

    override suspend fun getOrdersWithCustomers(): List<OrderWithCustomer> {
        return orderDao.getOrdersWithCustomers(currentUser!!.uid)
    }

    override suspend fun getOrderByCustomerOwner(customerOwnerId: Int): List<Order> {
        return orderDao.getOrderByCustomerOwner(currentUser!!.uid, customerOwnerId)
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

    override suspend fun sendOrdersToSincronize(orders: List<Order>): Task<Void> {
        Log.i("#-# TESTE #-#", "repository - ordersWithCustomer: $orders")
        val newOrder = hashMapOf(
            "orders" to orders
        )
        Log.i("#-# TESTE #-#", "repository - orders: $newOrder")

        return firestore
                .collection("orders")
                .document(currentUser!!.uid)
                .set(newOrder)
                .addOnSuccessListener { Log.d("#-# TESTE #-#", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("#-# TESTE #-#", "Error writing document", e) }

    }

}