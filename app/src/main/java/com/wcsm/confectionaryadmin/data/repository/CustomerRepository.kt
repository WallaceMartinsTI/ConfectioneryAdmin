package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer

interface CustomerRepository {

    suspend fun insertCustomer(customer: Customer)

    suspend fun updateCustomer(customer: Customer)

    suspend fun getAllCustomers(): List<Customer>

    suspend fun deleteCustomer(customer: Customer)

    suspend fun sendCustomersToSincronize(customers: List<Customer>): Task<Void>
}