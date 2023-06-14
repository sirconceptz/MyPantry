package com.hermanowicz.pantry.domain.scanner

import com.hermanowicz.pantry.di.repository.ScannerRepository
import com.hermanowicz.pantry.utils.enums.ScannerMethod
import javax.inject.Inject

class SetScannerMethodUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) : (ScannerMethod) -> Unit {
    override fun invoke(scannerMethod: ScannerMethod) {
        scannerRepository.setScannerMethod(scannerMethod)
    }
}
