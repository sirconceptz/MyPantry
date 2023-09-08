package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ObserveAppSettingsUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var observeAppSettingsUseCase: ObserveAppSettingsUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        observeAppSettingsUseCase = ObserveAppSettingsUseCase(settingsRepository)
    }

    @Test
    fun `test ObserveAppSettingsUseCase`() {
        val appSettingsFlow: Flow<AppSettings> = flowOf(AppSettings())

        `when`(settingsRepository.appSettings).thenReturn(appSettingsFlow)

        val result = runBlocking {
            observeAppSettingsUseCase().toList()
        }

        val expectedResults = listOf(AppSettings())
        assertEquals(expectedResults, result)
    }
}
