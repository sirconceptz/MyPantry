package com.hermanowicz.pantry.domain.errorAlertSystem

import javax.inject.Inject

class GetErrorCodeUseCase @Inject constructor() : (String) -> Int? {
    override fun invoke(errorStatementTitle: String): Int? {
        val errorCode = errorStatementTitle.substring(IntRange(3, 7))
        val code = errorCode.toIntOrNull()
        return code
    }
}
