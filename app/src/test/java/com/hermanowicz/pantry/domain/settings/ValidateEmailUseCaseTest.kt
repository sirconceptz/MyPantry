package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.utils.enums.EmailValidation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {
    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `test ValidateEmailUseCase with valid email`() {
        val email = "test@example.com"

        val result = validateEmailUseCase(email)

        assertEquals(EmailValidation.VALID, result)
    }

    @Test
    fun `test ValidateEmailUseCase with invalid email`() {
        val email = "invalid-email"

        val result = validateEmailUseCase(email)

        assertEquals(EmailValidation.INVALID, result)
    }

    @Test
    fun `test ValidateEmailUseCase with empty email`() {
        val email = ""

        val result = validateEmailUseCase(email)

        assertEquals(EmailValidation.EMPTY, result)
    }
}
