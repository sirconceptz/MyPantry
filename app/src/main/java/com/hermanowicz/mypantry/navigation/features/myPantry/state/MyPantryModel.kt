package com.hermanowicz.mypantry.navigation.features.myPantry.state

import com.hermanowicz.mypantry.data.model.GroupProduct

data class MyPantryModel(
    val groupsProduct: List<GroupProduct> = emptyList(),
    val loadingVisible: Boolean = true
)
