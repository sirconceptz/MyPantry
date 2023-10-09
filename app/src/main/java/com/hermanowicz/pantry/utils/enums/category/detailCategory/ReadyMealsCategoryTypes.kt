package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class ReadyMealsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    READY_MEALS(R.string.ready_meals)
}
