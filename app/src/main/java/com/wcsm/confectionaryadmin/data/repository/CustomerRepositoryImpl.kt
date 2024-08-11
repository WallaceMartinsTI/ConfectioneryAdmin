package com.wcsm.confectionaryadmin.data.repository

import com.wcsm.confectionaryadmin.data.database.CustomerDao
import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.data.model.CustomerWithOrders
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val customerDao: CustomerDao
) : CustomerRepository {
    override suspend fun getAllCustomers(): List<Customer> {
        return customerDao.getAllCustomers()
    }

    override suspend fun getCustomersWithOrders(): List<CustomerWithOrders> {
        return customerDao.getCustomersWithOrders()
    }

    override suspend fun insertCustomer(customer: Customer) {
        return customerDao.insertCustomer(customer)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }
}