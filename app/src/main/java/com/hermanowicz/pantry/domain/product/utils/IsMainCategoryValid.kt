package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.utils.category.MainCategories

class IsMainCategoryValid {
    companion object {
        fun isMainCategoryValid(
            productMainCategory: String,
            filterProductMainCategory: String
        ): Boolean {
            return filterProductMainCategory == MainCategories.CHOOSE.name || filterProductMainCategory.isEmpty() || productMainCategory == filterProductMainCategory || productMainCategory.isEmpty()
        }
    }
}
