package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class HerbsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    DRIED(R.string.dried),
    OTHER(R.string.other)
}
