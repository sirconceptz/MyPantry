package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchEmailAddressForNotificationsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<String> {
    override fun invoke(): Flow<String> {
        return settingsRepository.emailAddressForNotifications
    }
}