package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class LiqueursCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    LIQUEURS(R.string.liqueurs)
}
