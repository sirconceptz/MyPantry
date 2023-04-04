package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class ReadyMealsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    READY_MEALS(R.string.ready_meals)
}
