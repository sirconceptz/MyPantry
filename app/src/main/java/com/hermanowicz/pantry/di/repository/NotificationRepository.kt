package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.data.repository.NotificationRepositoryImpl
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface NotificationRepository {
    suspend fun createNotification(products: List<Product>)
    fun cancelNotification(productIdList: List<Int>)
    suspend fun createNotificationForAllProducts(databaseMode: DatabaseMode)
    suspend fun cancelNotificationForAllProducts(databaseMode: DatabaseMode)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationRepositoryModule {

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}
