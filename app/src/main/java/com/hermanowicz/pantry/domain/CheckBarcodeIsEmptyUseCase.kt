package com.hermanowicz.pantry.domain

import javax.inject.Inject

class CheckBarcodeIsEmptyUseCase @Inject constructor() : (String) -> Boolean {
    override fun invoke(barcode: String): Boolean {
        return barcode != "0"
    }
}