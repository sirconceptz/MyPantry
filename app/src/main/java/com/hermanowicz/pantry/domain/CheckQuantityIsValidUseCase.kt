package com.hermanowicz.pantry.domain

import javax.inject.Inject

class CheckQuantityIsValidUseCase @Inject constructor() : (Int?) -> Boolean {
    override fun invoke(quantity: Int?): Boolean {
        return if (quantity != null) {
            !(quantity < 1 || quantity > 10000) // quantity valid range - 1 - 10000
        } else
            true                                // true for empty value, parsed later to 1
    }
}