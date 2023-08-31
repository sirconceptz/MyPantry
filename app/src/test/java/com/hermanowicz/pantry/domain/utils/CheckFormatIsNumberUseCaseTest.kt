package com.hermanowicz.pantry.domain.utils

import org.junit.Before
import org.junit.Test

class CheckFormatIsNumberUseCaseTest {

    private lateinit var checkFormatIsNumberUseCase: CheckFormatIsNumberUseCase

    @Before
    fun setup() {
        checkFormatIsNumberUseCase = CheckFormatIsNumberUseCase()
    }

    @Test
    fun `test invoke returns true for valid number`() {
        val validNumber = "12345"
        val result = checkFormatIsNumberUseCase.invoke(validNumber)
        assert(result)
    }

    @Test
    fun `test invoke returns false for non-number text`() {
        val nonNumberText = "abc123"
        val result = checkFormatIsNumberUseCase.invoke(nonNumberText)
        assert(!result)
    }

    @Test
    fun `test invoke returns true for empty text`() {
        val emptyText = ""
        val result = checkFormatIsNumberUseCase.invoke(emptyText)
        assert(result)
    }
}
