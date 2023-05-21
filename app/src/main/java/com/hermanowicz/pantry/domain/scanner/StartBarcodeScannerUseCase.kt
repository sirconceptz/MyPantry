package com.hermanowicz.pantry.domain.scanner

import com.hermanowicz.pantry.di.repository.ScannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartBarcodeScannerUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) : () -> Flow<String> {
    override fun invoke(): Flow<String> {
        return scannerRepository.startBarcodeScanning()
    }
}