package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class LiqueursCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    LIQUEURS(R.string.liqueurs)
}
