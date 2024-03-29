package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class VinegarsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    APPLE(R.string.apple),
    HERB(R.string.herb),
    FRUIT(R.string.fruit),
    OTHER(R.string.other)
}
