package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FetchQrCodeSizeUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var fetchQrCodeSizeUseCase: FetchQrCodeSizeUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        fetchQrCodeSizeUseCase = FetchQrCodeSizeUseCase(settingsRepository)
    }

    @Test
    fun `test FetchQrCodeSizeUseCase`() {
        val qrCodeSizeFlow: Flow<String> = flowOf(QrCodeSize.BIG.name)

        `when`(settingsRepository.qrCodeSize).thenReturn(qrCodeSizeFlow)

        val result = runBlocking {
            fetchQrCodeSizeUseCase().toList()
        }

        val expectedResults = listOf(QrCodeSize.BIG)
        assertEquals(expectedResults, result)
    }
}
