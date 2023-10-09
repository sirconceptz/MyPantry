package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class ChemicalProductsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    CLEANING_PRODUCTS(R.string.cleaning_products),
    LIQUIDS(R.string.liquids),
    POWDERS(R.string.powders),
    OTHER(R.string.other)
}
