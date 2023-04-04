package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class VinegarsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    APPLE(R.string.apple),
    HERB(R.string.herb),
    FRUIT(R.string.fruit),
    OTHER(R.string.other)
}
