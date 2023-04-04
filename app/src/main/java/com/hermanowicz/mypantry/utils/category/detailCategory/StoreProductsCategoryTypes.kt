package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class StoreProductsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    STORE_PRODUCTS(R.string.store_products),
}
