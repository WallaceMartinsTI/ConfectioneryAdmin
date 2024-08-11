package com.wcsm.confectionaryadmin.di

import android.content.Context
import androidx.room.Room
import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.database.ConfectionaryAdminDatabase
import com.wcsm.confectionaryadmin.data.database.CustomerDao
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import com.wcsm.confectionaryadmin.data.repository.CustomerRepositoryImpl
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.data.repository.OrderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOrderDatabase(
        @ApplicationContext context: Context
    ): ConfectionaryAdminDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ConfectionaryAdminDatabase::class.java,
            "confectionary_admin_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(
        database: ConfectionaryAdminDatabase
    ): OrderDao {
        return database.orderDao
    }

    @Provides
    @Singleton
    fun provideCustomerDao(
        database: ConfectionaryAdminDatabase
    ): CustomerDao {
        return database.customerDao
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderDao: OrderDao
    ): OrderRepository {
        return OrderRepositoryImpl(orderDao)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(
        customerDao: CustomerDao
    ): CustomerRepository {
        return CustomerRepositoryImpl(customerDao)
    }

}