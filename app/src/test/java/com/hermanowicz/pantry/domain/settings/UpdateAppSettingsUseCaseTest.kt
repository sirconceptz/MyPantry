package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.di.repository.SettingsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateAppSettingsUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var updateAppSettingsUseCase: UpdateAppSettingsUseCase

    @Before
    fun setUp() {
        settingsRepository = mockk(relaxed = true)
        updateAppSettingsUseCase = UpdateAppSettingsUseCase(settingsRepository)
    }

    @Test
    fun `test UpdateAppSettingsUseCase`() {
        val appSettings = AppSettings(
            pushNotifications = true,
            emailNotifications = true
        )

        runBlocking {
            updateAppSettingsUseCase(appSettings)
        }

        coVerify { settingsRepository.updateAppSettings(appSettings) }
    }
}
