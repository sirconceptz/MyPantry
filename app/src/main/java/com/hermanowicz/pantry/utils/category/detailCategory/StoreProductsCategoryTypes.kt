package com.hermanowicz.pantry.utils.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.category.CategoryType

enum class StoreProductsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    STORE_PRODUCTS(R.string.store_products),
}
