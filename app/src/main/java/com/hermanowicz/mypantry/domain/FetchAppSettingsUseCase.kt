package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.settings.AppSettings
import com.hermanowicz.mypantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<AppSettings> {

    override fun invoke(): Flow<AppSettings> {
        return settingsRepository.appSettings
    }

}