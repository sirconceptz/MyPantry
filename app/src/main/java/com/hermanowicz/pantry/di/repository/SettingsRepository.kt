package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.data.repository.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val appSettings: Flow<AppSettings>
    val databaseMode: Flow<String>
    val scanCameraMode: Flow<String>
    val qrCodeSize: Flow<String>
    val daysBeforeNotification: Flow<Int>
    val isPushNotificationsEnabled: StateFlow<Boolean>
    val isEmailNotificationsEnabled: Flow<Boolean>
    val emailAddressForNotifications: Flow<String>
    suspend fun updateAppSettings(appSettings: AppSettings)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {

    @Binds
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}
