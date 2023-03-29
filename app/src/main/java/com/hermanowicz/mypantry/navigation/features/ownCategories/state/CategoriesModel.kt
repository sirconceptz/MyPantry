package com.hermanowicz.mypantry.navigation.features.ownCategories.state

import com.hermanowicz.mypantry.data.model.Category

data class CategoriesModel(
    var categories: List<Category> = emptyList(),
    var loadingVisible: Boolean = true
)
