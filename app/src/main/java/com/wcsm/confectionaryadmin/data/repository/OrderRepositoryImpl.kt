package com.wcsm.confectionaryadmin.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val firestore: FirebaseFirestore
) : OrderRepository {
    override suspend fun getOrdersWithCustomers(userOwnerId: String): List<OrderWithCustomer> {
        return orderDao.getOrdersWithCustomers(userOwnerId)
    }

    override suspend fun getOrderByCustomerOwner(userOwnerId: String, customerOwnerId: String): List<Order> {
        return orderDao.getOrderByCustomerOwner(userOwnerId, customerOwnerId)
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

    override suspend fun getUserOrdersQuantity(userOwnerId: String): Int {
        return orderDao.getOrdersQuantity(userOwnerId)
    }

    override suspend fun sendOrdersToSincronize(userOwnerId: String, orders: List<Order>): Task<Void> {
        val newOrder = hashMapOf(
            "orders" to orders
        )

        return firestore
                .collection("orders")
                .document(userOwnerId)
                .set(newOrder)
                .addOnSuccessListener { Log.d("#-# TESTE #-#", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("#-# TESTE #-#", "Error writing document", e) }

    }

    override suspend fun getOrdersFromFirestore(userOwnerId: String): List<Order> {
        return try {
            val snapshot = firestore
                .collection("orders")
                .document(userOwnerId)
                .get()
                .await()
            val data = snapshot.data?.get("orders") as? List<Map<String, Any>> ?: emptyList()

            data.map { map ->
                Order(
                    orderId = map["orderId"] as? String ?: "",
                    userOrderOwnerId = map["userOrderOwnerId"] as? String ?: "",
                    customerOwnerId = map["customerOwnerId"] as? String ?: "",
                    title = map["title"] as? String ?: "",
                    description = map["description"] as? String ?: "",
                    price = map["price"] as? Double ?: 0.0,
                    status = OrderStatus.valueOf(map["status"] as? String ?: "PENDING"),
                    orderDate = (map["orderDate"] as? Long) ?: 0L,
                    deliverDate = (map["deliverDate"] as? Long) ?: 0L
                )
            }
        } catch (e: Exception) {
            Log.e("SyncError", "Error getting orders from Firestore", e)
            emptyList()
        }
    }

    override suspend fun saveOrdersToLocalDatabase(orders: List<Order>) {
        orderDao.insertOrders(orders)
    }

    override suspend fun deleteAllUserOrdersRoom(userOwnerId: String) {
        orderDao.deleteAllUserOrders(userOwnerId)
    }

    override suspend fun deleteAllUserOrdersFirestore(userOwnerId: String): Task<Void> {
        return firestore
            .collection("orders")
            .document(userOwnerId)
            .delete()
    }

}