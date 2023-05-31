package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.di.repository.SettingsRepository
import javax.inject.Inject

class UpdateAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : suspend (AppSettings) -> Unit {
    override suspend fun invoke(appSettings: AppSettings) {
        settingsRepository.updateAppSettings(appSettings)
    }
}
