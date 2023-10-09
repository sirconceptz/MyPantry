package com.hermanowicz.pantry.utils.enums.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.enums.category.CategoryType

enum class FruitsCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    JAM(R.string.jam),
    JUICE(R.string.juice),
    SYRUP(R.string.syrup),
    MOUSSE(R.string.mousse),
    COMPOTE(R.string.compote),
    JELLY(R.string.jelly),
    MARMALADES(R.string.marmalades),
    FRESH(R.string.fresh),
    OTHER(R.string.other)
}
