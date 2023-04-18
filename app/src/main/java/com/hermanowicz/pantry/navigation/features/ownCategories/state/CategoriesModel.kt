package com.hermanowicz.pantry.navigation.features.ownCategories.state

import com.hermanowicz.pantry.data.model.Category

data class CategoriesModel(
    var categories: List<Category> = emptyList(),
    var loadingVisible: Boolean = true
)
