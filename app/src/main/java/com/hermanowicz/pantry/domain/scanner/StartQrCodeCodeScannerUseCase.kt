package com.hermanowicz.pantry.domain.scanner

import com.hermanowicz.pantry.di.repository.ScannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartQrCodeCodeScannerUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) : () -> Flow<Pair<Int, String>> {
    override fun invoke(): Flow<Pair<Int, String>> {
        return scannerRepository.startQRCodeScanning()
    }
}