package com.hermanowicz.pantry.utils.enums.category

import com.hermanowicz.pantry.R

enum class MainCategories(override val nameResId: Int) : CategoryType {
    CHOOSE(R.string.choose),
    OWN_CATEGORIES(R.string.own_categories),
    STORE_PRODUCTS(R.string.store_products),
    READY_MEALS(R.string.ready_meals),
    VEGETABLES(R.string.vegetables),
    FRUITS(R.string.fruits),
    HERBS(R.string.herbs),
    LIQUORS(R.string.liquors),
    WINES(R.string.wines),
    MUSHROOMS(R.string.mushrooms),
    VINEGARS(R.string.vinegars),
    CHEMICAL_PRODUCTS(R.string.chemical_products),
    OTHER(R.string.other)
}
