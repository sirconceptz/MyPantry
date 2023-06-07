package com.hermanowicz.pantry.domain.scanner

import com.hermanowicz.pantry.di.repository.ScannerRepository
import javax.inject.Inject

class DecodeQrCodeUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) : (String) -> Pair<Int, String> {
    override fun invoke(scanResult: String): Pair<Int, String> {
        return scannerRepository.decodeScanQRCodeResult(scanResult)
    }
}