package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ObserveDatabaseModeUseCaseTest {
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var observeDatabaseModeUseCase: ObserveDatabaseModeUseCase

    @Before
    fun setUp() {
        settingsRepository = mock(SettingsRepository::class.java)
        observeDatabaseModeUseCase = ObserveDatabaseModeUseCase(settingsRepository)
    }

    @Test
    fun `test ObserveDatabaseModeUseCase`() {
        val databaseModeFlow: Flow<String> = flowOf(DatabaseMode.LOCAL.name)

        `when`(settingsRepository.databaseMode).thenReturn(databaseModeFlow)

        val result = runBlocking {
            observeDatabaseModeUseCase().toList()
        }

        val expectedResults = listOf(DatabaseMode.LOCAL)
        assertEquals(expectedResults, result)
    }
}
