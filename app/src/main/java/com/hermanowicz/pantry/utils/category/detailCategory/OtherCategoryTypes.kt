package com.hermanowicz.pantry.utils.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.category.CategoryType

enum class OtherCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    OTHER(R.string.other)
}
