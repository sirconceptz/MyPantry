package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.settings.AppSettings
import com.hermanowicz.pantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<AppSettings> {

    override fun invoke(): Flow<AppSettings> {
        return settingsRepository.appSettings
    }

}