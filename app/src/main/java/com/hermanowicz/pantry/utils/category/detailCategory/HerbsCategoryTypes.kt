package com.hermanowicz.pantry.utils.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.category.CategoryType

enum class HerbsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    DRIED(R.string.dried),
    OTHER(R.string.other)
}
