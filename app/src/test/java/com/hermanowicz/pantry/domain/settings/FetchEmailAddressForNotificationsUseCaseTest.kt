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

class FetchEmailAddressForNotificationsUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var fetchEmailAddressForNotificationsUseCase: FetchEmailAddressForNotificationsUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        fetchEmailAddressForNotificationsUseCase = FetchEmailAddressForNotificationsUseCase(settingsRepository)
    }

    @Test
    fun `test FetchEmailAddressForNotificationsUseCase`() {
        val emailAddressFlow: Flow<String> = flowOf("example@example.com")

        `when`(settingsRepository.emailAddressForNotifications).thenReturn(emailAddressFlow)

        val result = runBlocking {
            fetchEmailAddressForNotificationsUseCase().toList()
        }

        val expectedResults = listOf("example@example.com")
        assertEquals(expectedResults, result)
    }
}
