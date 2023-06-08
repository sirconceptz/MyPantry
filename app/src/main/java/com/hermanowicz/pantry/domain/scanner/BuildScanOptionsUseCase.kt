package com.hermanowicz.pantry.domain.scanner

import com.hermanowicz.pantry.di.repository.ScannerRepository
import com.journeyapps.barcodescanner.ScanOptions
import javax.inject.Inject

class BuildScanOptionsUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) : suspend () -> ScanOptions {
    override suspend fun invoke(): ScanOptions {
        return scannerRepository.buildScanOptions()
    }
}
