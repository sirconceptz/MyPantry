package com.hermanowicz.pantry.domain.scanner

import javax.inject.Inject

class CheckBarcodeIsEmptyUseCase @Inject constructor() : (String) -> Boolean {
    override fun invoke(barcode: String): Boolean {
        return barcode != "-1"
    }
}
