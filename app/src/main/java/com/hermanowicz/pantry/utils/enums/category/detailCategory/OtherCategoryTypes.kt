package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class OtherCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    OTHER(R.string.other)
}
