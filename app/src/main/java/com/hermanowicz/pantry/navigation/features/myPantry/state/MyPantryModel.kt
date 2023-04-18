package com.hermanowicz.pantry.navigation.features.myPantry.state

import com.hermanowicz.pantry.data.model.GroupProduct

data class MyPantryModel(
    var groupsProduct: List<GroupProduct> = emptyList(),
    var loadingVisible: Boolean = true
)
