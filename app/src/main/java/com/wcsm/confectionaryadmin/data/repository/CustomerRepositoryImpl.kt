package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.database.CustomerDao
import com.wcsm.confectionaryadmin.data.model.entities.Customer
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
}