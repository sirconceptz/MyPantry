package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchQrCodeSizeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : () -> Flow<QrCodeSize> {
    override fun invoke(): Flow<QrCodeSize> {
        return settingsRepository.qrCodeSize.map { enumValueOf(it) }
    }
}
