package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.settings.AppSettings
import com.hermanowicz.mypantry.di.repository.SettingsRepository
import javax.inject.Inject

class UpdateAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : suspend (AppSettings) -> Unit {
    override suspend fun invoke(appSettings: AppSettings) {
        settingsRepository.updateAppSettings(appSettings)
    }
}