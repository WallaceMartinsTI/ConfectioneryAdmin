package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.model.Customer

interface CustomerRepository {

    suspend fun insertCustomer(customer: Customer)

    suspend fun updateCustomer(customer: Customer)

    suspend fun getAllCustomers(): List<Customer>

    suspend fun deleteCustomer(customer: Customer)
}