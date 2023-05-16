package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckIsErrorWasDisplayedUseCase @Inject constructor(
    private val repository: ErrorAlertSystemRepository,
    private val getErrorCodeUseCase: GetErrorCodeUseCase
) : suspend (String) -> Boolean {
    override suspend fun invoke(errorStatementTitle: String): Boolean {
        val allErrorCodes = repository.observeAllDisplayedErrors().first()
        val code = getErrorCodeUseCase(errorStatementTitle)
        if (code != null) {
            allErrorCodes.forEach { checkedCode ->
                if (checkedCode == code)
                    return true
            }
            return false
        }
        return false
    }

}