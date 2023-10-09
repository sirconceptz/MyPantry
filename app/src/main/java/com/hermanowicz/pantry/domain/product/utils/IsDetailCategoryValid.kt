package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes

class IsDetailCategoryValid {
    companion object {
        fun isDetailCategoryValid(
            productDetailCategory: String,
            filterProductDetailCategory: String
        ): Boolean {
            return filterProductDetailCategory == ChooseCategoryTypes.CHOOSE.name || filterProductDetailCategory.isEmpty() || productDetailCategory == filterProductDetailCategory
        }
    }
}
