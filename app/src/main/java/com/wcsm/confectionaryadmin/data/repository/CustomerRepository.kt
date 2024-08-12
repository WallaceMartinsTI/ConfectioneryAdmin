package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.CustomerWithOrders

interface CustomerRepository {

    suspend fun getCustomersWithOrders(): List<CustomerWithOrders>

    suspend fun insertCustomer(customer: Customer)

    suspend fun deleteCustomer(customer: Customer)
}