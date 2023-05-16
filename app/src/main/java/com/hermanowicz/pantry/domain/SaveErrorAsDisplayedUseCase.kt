package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import javax.inject.Inject

class SaveErrorAsDisplayedUseCase @Inject constructor(
    private val errorAlertSystemRepository: ErrorAlertSystemRepository
) : suspend (Int) -> Unit {
    override suspend fun invoke(errorCode: Int) {
        errorAlertSystemRepository.insert(errorCode)
    }
}