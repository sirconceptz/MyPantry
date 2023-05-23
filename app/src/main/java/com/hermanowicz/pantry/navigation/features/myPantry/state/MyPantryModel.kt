package com.hermanowicz.pantry.navigation.features.myPantry.state

import com.hermanowicz.pantry.data.model.GroupProduct

data class MyPantryModel(
    var groupProductList: List<GroupProduct> = emptyList(),
    var loadingVisible: Boolean = true
)
