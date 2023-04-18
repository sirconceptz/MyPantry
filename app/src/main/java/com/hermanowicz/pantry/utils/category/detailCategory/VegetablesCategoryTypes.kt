package com.hermanowicz.pantry.utils.category.detailCategory

import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.utils.category.CategoryType

enum class VegetablesCategoryTypes(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    PRESERVED(R.string.preserved),
    SALADS(R.string.salads),
    MARINATE(R.string.marinate),
    KETCHUPS(R.string.ketchups),
    FRESH(R.string.fresh),
    OTHER(R.string.other)
}
