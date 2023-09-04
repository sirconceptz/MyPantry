package com.hermanowicz.pantry.domain.errorAlertSystem

import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveErrorAsDisplayedUseCaseTest {
    private lateinit var errorAlertSystemRepository: ErrorAlertSystemRepository
    private lateinit var saveErrorAsDisplayedUseCase: SaveErrorAsDisplayedUseCase

    @Before
    fun setUp() {
        errorAlertSystemRepository = mockk()
        coEvery { errorAlertSystemRepository.insert(any()) } returns Unit
        saveErrorAsDisplayedUseCase = SaveErrorAsDisplayedUseCase(errorAlertSystemRepository)
    }

    @Test
    fun `test SaveErrorAsDisplayedUseCase`() {
        val errorCode = 12345

        runBlocking {
            saveErrorAsDisplayedUseCase(errorCode)
        }

        coVerify { errorAlertSystemRepository.insert(errorCode) }
    }
}
