package com.hermanowicz.pantry.domain.product.utils

class IsProductDateInRange {
    companion object {
        fun isProductionDateInRange(
            productProductionDate: String,
            filterProductionDateSince: String,
            filterProductionDateFor: String
        ): Boolean {
            val isProductionDateSinceValid =
                IsDateAfter.isDateAfter(productProductionDate, filterProductionDateSince)
            val isProductionDateForValid =
                IsDateAfter.isDateAfter(filterProductionDateFor, productProductionDate)
            return isProductionDateSinceValid && isProductionDateForValid
        }
    }
}
