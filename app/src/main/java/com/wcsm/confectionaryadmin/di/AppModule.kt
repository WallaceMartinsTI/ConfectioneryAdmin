package com.wcsm.confectionaryadmin.di

import android.content.Context
import androidx.room.Room
import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.database.OrderDatabase
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
    ): OrderDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            OrderDatabase::class.java,
            "order_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(
        database: OrderDatabase
    ): OrderDao {
        return database.orderDao
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderDao: OrderDao
    ): OrderRepository {
        return OrderRepositoryImpl(orderDao)
    }

}