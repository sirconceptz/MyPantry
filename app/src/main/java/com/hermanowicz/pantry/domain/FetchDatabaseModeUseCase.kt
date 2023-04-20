package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchDatabaseModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<DatabaseMode> {
    override fun invoke(): Flow<DatabaseMode> {
        return settingsRepository.databaseMode.map { enumValueOf(it) }
    }
}