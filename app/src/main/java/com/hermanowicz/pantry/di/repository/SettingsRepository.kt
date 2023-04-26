package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.repository.SettingsRepositoryImpl
import com.hermanowicz.pantry.data.settings.AppSettings
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val appSettings: Flow<AppSettings>
    val databaseMode: Flow<String>
    val qrCodeSize: Flow<String>
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
