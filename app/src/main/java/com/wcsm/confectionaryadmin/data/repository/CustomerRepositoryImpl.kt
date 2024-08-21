package com.wcsm.confectionaryadmin.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.database.CustomerDao
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerDao: CustomerDao,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : CustomerRepository {
    private val currentUser = auth.currentUser
    override suspend fun insertCustomer(customer: Customer) {
        return customerDao.insertCustomer(customer)
    }

    override suspend fun updateCustomer(customer: Customer) {
        return customerDao.updateCustomer(customer)
    }

    override suspend fun getAllCustomers(): List<Customer> {
        return customerDao.getAllCustomers(currentUser!!.uid)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }

    override suspend fun sendCustomersToSincronize(customers: List<Customer>): Task<Void> {
        val newCustomer = hashMapOf(
            "customers" to customers
        )
        return firestore
            .collection("customers")
            .document(currentUser!!.uid)
            .set(newCustomer)
    }

    override suspend fun getCustomersFromFirestore(): List<Customer> {
        return try {
            val snapshot = firestore
                .collection("customers")
                .document(currentUser!!.uid)
                .get()
                .await()
            val data = snapshot.data?.get("customers") as? List<Map<String, Any>> ?: emptyList()

            data.map { map ->
                Customer(
                    customerId = (map["customerId"] as Long).toInt(),
                    userCustomerOwnerId = map["userCustomerOwnerId"] as? String ?: "",
                    name = map["name"] as? String ?: "",
                    email = map["email"] as? String ?: "",
                    phone = map["phone"] as? String ?: "",
                    gender = map["gender"] as? String ?: "",
                    dateOfBirth = map["dateOfBirth"] as? String ?: "",
                    address = map["address"] as? String ?: "",
                    notes = map["notes"] as? String ?: "",
                    ordersQuantity = (map["ordersQuantity"] as? Long)?.toInt() ?: 0,
                    customerSince = (map["customerSince"] as? Long) ?: 0L
                )
            }
        } catch (e: Exception) {
            Log.e("SyncError", "Error getting customers from Firestore", e)
            emptyList()
        }
    }

    override suspend fun saveCustomersToLocalDatabase(customers: List<Customer>) {
        customerDao.insertCustomers(customers)
    }
}