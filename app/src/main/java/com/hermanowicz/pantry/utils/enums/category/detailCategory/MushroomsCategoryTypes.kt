package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class MushroomsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    MARINATE(R.string.marinate),
    SALT(R.string.salt),
    DRIED(R.string.dried),
    OTHER(R.string.other)
}
