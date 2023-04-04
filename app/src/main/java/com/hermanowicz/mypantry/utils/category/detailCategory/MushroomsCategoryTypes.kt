package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class MushroomsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    MARINATE(R.string.marinate),
    SALT(R.string.salt),
    DRIED(R.string.dried),
    OTHER(R.string.other)
}
