package com.wcsm.confectionaryadmin.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.database.CustomerDao
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerDao: CustomerDao,
    private val firestore: FirebaseFirestore
) : CustomerRepository {
    override suspend fun insertCustomer(customer: Customer) {
        return customerDao.insertCustomer(customer)
    }

    override suspend fun updateCustomer(customer: Customer) {
        return customerDao.updateCustomer(customer)
    }

    override suspend fun getAllCustomers(userOwnerId: String): List<Customer> {
        return customerDao.getAllCustomers(userOwnerId)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }

    override suspend fun sendCustomersToSync(userOwnerId: String, customers: List<Customer>): Task<Void> {
        val newCustomer = hashMapOf(
            "customers" to customers
        )
        return firestore
            .collection("customers")
            .document(userOwnerId)
            .set(newCustomer)
    }

    override suspend fun getCustomersFromFirestore(userOwnerId: String): List<Customer> {
        return try {
            val snapshot = firestore
                .collection("customers")
                .document(userOwnerId)
                .get()
                .await()
            val data = snapshot.data?.get("customers") as? List<Map<String, Any>> ?: emptyList()

            data.map { map ->
                Customer(
                    customerId = map["customerId"] as? String ?: "",
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

    override suspend fun getUserCustomersQuantity(userOwnerId: String): Int {
        return customerDao.getCustomersQuantity(userOwnerId)
    }

    override suspend fun deleteAllUserCustomersRoom(userOwnerId: String) {
        customerDao.deleteAllUserCustomers(userOwnerId)
    }

    override suspend fun deleteAllUserCustomersFirestore(userOwnerId: String): Task<Void> {
        return firestore
            .collection("customers")
            .document(userOwnerId)
            .delete()
    }
}