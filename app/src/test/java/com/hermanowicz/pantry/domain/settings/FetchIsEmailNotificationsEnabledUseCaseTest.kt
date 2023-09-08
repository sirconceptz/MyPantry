package com.hermanowicz.pantry.domain.settings

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

class FetchIsEmailNotificationsEnabledUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var fetchIsEmailNotificationsEnabledUseCase: FetchIsEmailNotificationsEnabledUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        fetchIsEmailNotificationsEnabledUseCase = FetchIsEmailNotificationsEnabledUseCase(settingsRepository)
    }

    @Test
    fun `test FetchIsEmailNotificationsEnabledUseCase when notifications are enabled`() {
        val isNotificationsEnabledFlow: Flow<Boolean> = flowOf(true)

        `when`(settingsRepository.isEmailNotificationsEnabled).thenReturn(isNotificationsEnabledFlow)

        val result = runBlocking {
            fetchIsEmailNotificationsEnabledUseCase().toList()
        }

        val expectedResults = listOf(true)
        assertEquals(expectedResults, result)
    }

    @Test
    fun `test FetchIsEmailNotificationsEnabledUseCase when notifications are disabled`() {
        val isNotificationsEnabledFlow: Flow<Boolean> = flowOf(false)

        `when`(settingsRepository.isEmailNotificationsEnabled).thenReturn(isNotificationsEnabledFlow)

        val result = runBlocking {
            fetchIsEmailNotificationsEnabledUseCase().toList()
        }

        val expectedResults = listOf(false)
        assertEquals(expectedResults, result)
    }
}
