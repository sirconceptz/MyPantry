package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class StoreProductsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    STORE_PRODUCTS(R.string.store_products)
}
