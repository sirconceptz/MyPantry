package com.hermanowicz.pantry.domain.errorAlertSystem

import com.hermanowicz.pantry.data.model.errorAlertSystem.ErrorAlert
import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchActiveErrorAlertsUseCaseTest {
    private lateinit var errorAlertSystemRepository: ErrorAlertSystemRepository
    private lateinit var fetchActiveErrorAlertsUseCase: FetchActiveErrorAlertsUseCase

    @Before
    fun setUp() {
        errorAlertSystemRepository = mockk()
        fetchActiveErrorAlertsUseCase = FetchActiveErrorAlertsUseCase(errorAlertSystemRepository)
    }

    @Test
    fun `test fetchActiveErrorAlertsUseCase when repository returns a list of ErrorAlerts`() {
        val expectedErrorAlerts = listOf(
            ErrorAlert(errorCode = 1, title = "ERR00001", message = "Important error"),
            ErrorAlert(errorCode = 2, title = "ERR00002", message = "Code: RED!")
        )

        coEvery { errorAlertSystemRepository.fetchAllRemoteErrors() } returns expectedErrorAlerts

        val result = runBlocking {
            fetchActiveErrorAlertsUseCase()
        }

        assertEquals(expectedErrorAlerts, result)
    }


    @Test
    fun `test fetchActiveErrorAlertsUseCase when repository returns an empty list`() {
        coEvery { errorAlertSystemRepository.fetchAllRemoteErrors() } returns emptyList()

        val result = runBlocking {
            fetchActiveErrorAlertsUseCase()
        }

        assertTrue(result.isEmpty())
    }
}
