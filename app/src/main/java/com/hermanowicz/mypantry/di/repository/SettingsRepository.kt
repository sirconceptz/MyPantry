package com.hermanowicz.mypantry.di.repository

import com.hermanowicz.mypantry.data.repository.SettingsRepositoryImpl
import com.hermanowicz.mypantry.data.settings.AppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val appSettings: Flow<AppSettings>
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
