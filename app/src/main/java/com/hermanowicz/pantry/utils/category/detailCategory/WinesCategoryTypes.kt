package com.hermanowicz.pantry.utils.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.category.CategoryType

enum class WinesCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    DRY(R.string.dry),
    SEMI_DRY(R.string.semi_dry),
    SWEET(R.string.sweet),
    SEMI_SWEET(R.string.semi_sweet)
}
