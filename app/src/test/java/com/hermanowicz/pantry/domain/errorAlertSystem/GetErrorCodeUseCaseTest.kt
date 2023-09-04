package com.hermanowicz.pantry.domain.errorAlertSystem

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetErrorCodeUseCaseTest {
    private lateinit var getErrorCodeUseCase: GetErrorCodeUseCase

    @Before
    fun setUp() {
        getErrorCodeUseCase = GetErrorCodeUseCase()
    }

    @Test
    fun `test GetErrorCodeUseCase when error code is present in the string`() {
        val errorStatementTitle = "ERR12345"

        val result = getErrorCodeUseCase(errorStatementTitle)

        assertEquals(12345, result)
    }

    @Test
    fun `test GetErrorCodeUseCase when error code is not present in the string`() {
        val errorStatementTitle = "This is a sample error message"

        val result = getErrorCodeUseCase(errorStatementTitle)

        assertEquals(null, result)
    }
}
