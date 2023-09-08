package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.domain.product.utils.IsDateAfter.Companion.isDateAfter

class IsExpirationDateInRange {
    companion object {
        fun isExpirationDateInRange(
            productExpirationDate: String,
            filterExpirationDateSince: String,
            filterExpirationDateFor: String
        ): Boolean {
            val isExpirationDateSinceValid =
                isDateAfter(productExpirationDate, filterExpirationDateSince)
            val isExpirationDateForValid =
                isDateAfter(filterExpirationDateFor, productExpirationDate)
            return isExpirationDateSinceValid && isExpirationDateForValid
        }
    }
}
