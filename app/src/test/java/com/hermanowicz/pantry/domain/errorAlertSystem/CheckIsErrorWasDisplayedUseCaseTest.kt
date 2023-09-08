package com.hermanowicz.pantry.domain.errorAlertSystem

import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CheckIsErrorWasDisplayedUseCaseTest {
    private lateinit var repository: ErrorAlertSystemRepository
    private lateinit var getErrorCodeUseCase: GetErrorCodeUseCase
    private lateinit var checkIsErrorWasDisplayedUseCase: CheckIsErrorWasDisplayedUseCase

    @Before
    fun setUp() {
        repository = mock(ErrorAlertSystemRepository::class.java)
        getErrorCodeUseCase = mock(GetErrorCodeUseCase::class.java)
        checkIsErrorWasDisplayedUseCase = CheckIsErrorWasDisplayedUseCase(repository, getErrorCodeUseCase)
    }

    @Test
    fun `test when error code is found in the list`() {
        // Mock the behavior of the repository to return a list of error codes
        val errorCodesFlow: Flow<List<Int>> = flowOf(listOf(1))
        `when`(repository.observeAllDisplayedErrors()).thenReturn(errorCodesFlow)

        // Mock the behavior of getErrorCodeUseCase
        `when`(getErrorCodeUseCase("ERR00001")).thenReturn(1)

        val result = runBlocking {
            checkIsErrorWasDisplayedUseCase("ERR00001")
        }

        assertTrue(result)
    }

    @Test
    fun `test when error code is not found in the list`() {
        // Mock the behavior of the repository to return a list of error codes
        val errorCodesFlow: Flow<List<Int>> = flowOf(listOf(1, 2))
        `when`(repository.observeAllDisplayedErrors()).thenReturn(errorCodesFlow)

        // Mock the behavior of getErrorCodeUseCase
        `when`(getErrorCodeUseCase("ERR00003")).thenReturn(3)

        val result = runBlocking {
            checkIsErrorWasDisplayedUseCase("ERR00003")
        }

        assertFalse(result)
    }
}
