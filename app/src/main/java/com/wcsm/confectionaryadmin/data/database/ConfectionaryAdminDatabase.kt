package com.wcsm.confectionaryadmin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order

@Database(
    entities = [Order::class, Customer::class],
    version = 1
)
abstract class ConfectionaryAdminDatabase: RoomDatabase() {
    abstract val orderDao: OrderDao
    abstract val customerDao: CustomerDao
}