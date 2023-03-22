package com.hermanowicz.mypantry.navigation.features.myPantry.state

import com.hermanowicz.mypantry.data.model.GroupProduct

data class MyPantryModel(
    var groupsProduct: List<GroupProduct> = emptyList(),
    var loadingVisible: Boolean = true
)
