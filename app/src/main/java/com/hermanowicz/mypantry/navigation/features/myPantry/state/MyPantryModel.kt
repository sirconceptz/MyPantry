package com.hermanowicz.mypantry.navigation.features.myPantry.state

import com.hermanowicz.mypantry.data.local.model.ProductEntity

data class MyPantryModel(
    val products: List<ProductEntity> = emptyList()
)
