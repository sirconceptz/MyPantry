package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FetchDaysBeforeNotificationUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var fetchDaysBeforeNotificationUseCase: FetchDaysBeforeNotificationUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        fetchDaysBeforeNotificationUseCase = FetchDaysBeforeNotificationUseCase(settingsRepository)
    }

    @Test
    fun `test FetchDaysBeforeNotificationUseCase`() {
        val daysBeforeNotificationFlow: Flow<Int> = flowOf(7)

        `when`(settingsRepository.daysBeforeNotification).thenReturn(daysBeforeNotificationFlow)

        val result = runBlocking {
            fetchDaysBeforeNotificationUseCase().toList()
        }

        val expectedResults = listOf(7)
        assertEquals(expectedResults, result)
    }
}
