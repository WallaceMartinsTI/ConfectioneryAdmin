package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.wcsm.confectionaryadmin.data.model.entities.Customer

interface CustomerRepository {
    suspend fun insertCustomer(customer: Customer)

    suspend fun updateCustomer(customer: Customer)

    suspend fun getAllCustomers(userOwnerId: String): List<Customer>

    suspend fun deleteCustomer(customer: Customer)

    suspend fun sendCustomersToSync(userOwnerId: String, customers: List<Customer>): Task<Void>

    suspend fun getCustomersFromFirestore(userOwnerId: String): List<Customer>

    suspend fun saveCustomersToLocalDatabase(customers: List<Customer>)

    suspend fun getUserCustomersQuantity(userOwnerId: String): Int

    suspend fun deleteAllUserCustomersRoom(userOwnerId: String)

    suspend fun deleteAllUserCustomersFirestore(userOwnerId: String): Task<Void>
}