package com.hermanowicz.mypantry.utils.category.detailCategory

import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.utils.category.CategoryType

enum class VegetablesCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    PRESERVED(R.string.preserved),
    SALADS(R.string.salads),
    MARINATE(R.string.marinate),
    KETCHUPS(R.string.ketchups),
    FRESH(R.string.fresh),
    OTHER(R.string.other)
}
