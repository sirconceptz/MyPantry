package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

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
