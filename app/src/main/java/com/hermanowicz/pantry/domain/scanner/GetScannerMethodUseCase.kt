package com.hermanowicz.pantry.domain.scanner

import com.hermanowicz.pantry.di.repository.ScannerRepository
import com.hermanowicz.pantry.utils.enums.ScannerMethod
import javax.inject.Inject

class GetScannerMethodUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) : () -> ScannerMethod {
    override fun invoke(): ScannerMethod {
        return scannerRepository.getScannerMethod()
    }
}
