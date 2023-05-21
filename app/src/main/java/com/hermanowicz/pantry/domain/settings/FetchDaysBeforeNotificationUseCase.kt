package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDaysBeforeNotificationUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<Int> {
    override fun invoke(): Flow<Int> {
        return settingsRepository.daysBeforeNotification
    }
}