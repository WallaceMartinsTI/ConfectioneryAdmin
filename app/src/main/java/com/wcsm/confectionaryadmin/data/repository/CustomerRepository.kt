package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.model.Customer

interface CustomerRepository {

    suspend fun getAllCustomers(): List<Customer>

    suspend fun insertCustomer(customer: Customer)

    suspend fun deleteCustomer(customer: Customer)
}