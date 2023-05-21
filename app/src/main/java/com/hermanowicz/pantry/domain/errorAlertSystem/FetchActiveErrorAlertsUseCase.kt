package com.hermanowicz.pantry.domain.errorAlertSystem

import com.hermanowicz.pantry.data.model.errorAlertSystem.ErrorAlert
import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import javax.inject.Inject

class FetchActiveErrorAlertsUseCase @Inject constructor(
    private val errorAlertSystemRepository: ErrorAlertSystemRepository
) : suspend () -> List<ErrorAlert> {
    override suspend fun invoke(): List<ErrorAlert> {
        return errorAlertSystemRepository.observeAllRemoteErrors()
    }
}