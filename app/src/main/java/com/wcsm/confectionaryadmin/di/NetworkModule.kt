package com.wcsm.confectionaryadmin.di

import android.content.Context
import android.net.ConnectivityManager
import com.wcsm.confectionaryadmin.data.repository.NetworkRepository
import com.wcsm.confectionaryadmin.data.repository.NetworkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideNetworkRepository(connectivityManager: ConnectivityManager): NetworkRepository {
        return NetworkRepositoryImpl(connectivityManager)
    }

}