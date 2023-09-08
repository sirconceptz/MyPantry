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

class FetchIsPushNotificationsEnabledUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var fetchIsPushNotificationsEnabledUseCase: FetchIsPushNotificationsEnabledUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        fetchIsPushNotificationsEnabledUseCase = FetchIsPushNotificationsEnabledUseCase(settingsRepository)
    }

    @Test
    fun `test FetchIsPushNotificationsEnabledUseCase when push notifications are enabled`() {
        val isPushNotificationsEnabledFlow: Flow<Boolean> = flowOf(true)

        `when`(settingsRepository.isPushNotificationsEnabled).thenReturn(isPushNotificationsEnabledFlow)

        val result = runBlocking {
            fetchIsPushNotificationsEnabledUseCase().toList()
        }

        val expectedResults = listOf(true)
        assertEquals(expectedResults, result)
    }

    @Test
    fun `test FetchIsPushNotificationsEnabledUseCase when push notifications are disabled`() {
        val isPushNotificationsEnabledFlow: Flow<Boolean> = flowOf(false)

        `when`(settingsRepository.isPushNotificationsEnabled).thenReturn(isPushNotificationsEnabledFlow)

        val result = runBlocking {
            fetchIsPushNotificationsEnabledUseCase().toList()
        }

        val expectedResults = listOf(false)
        assertEquals(expectedResults, result)
    }
}
