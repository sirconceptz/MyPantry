package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class OtherCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    OTHER(R.string.other)
}
