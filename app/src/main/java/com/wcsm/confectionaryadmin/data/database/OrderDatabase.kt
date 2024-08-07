package com.wcsm.confectionaryadmin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wcsm.confectionaryadmin.data.model.Order

@Database(
    entities = [Order::class],
    version = 1
)
abstract class OrderDatabase: RoomDatabase() {
    abstract val orderDao: OrderDao
}