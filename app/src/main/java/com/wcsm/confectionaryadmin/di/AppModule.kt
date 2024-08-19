package com.wcsm.confectionaryadmin.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.database.OrderDao
import com.wcsm.confectionaryadmin.data.database.ConfectionaryAdminDatabase
import com.wcsm.confectionaryadmin.data.database.CustomerDao
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import com.wcsm.confectionaryadmin.data.repository.CustomerRepositoryImpl
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.data.repository.OrderRepositoryImpl
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import com.wcsm.confectionaryadmin.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Room
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

    // Firebase
    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserRepository {
        return UserRepositoryImpl(auth, firestore)
    }

    // Network


}