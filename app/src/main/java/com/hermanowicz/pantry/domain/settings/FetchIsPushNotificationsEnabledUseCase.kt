package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchIsPushNotificationsEnabledUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<Boolean> {
    override fun invoke(): Flow<Boolean> {
        return settingsRepository.isPushNotificationsEnabled
    }
}
