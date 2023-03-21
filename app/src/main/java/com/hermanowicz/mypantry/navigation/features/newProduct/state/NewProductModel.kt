package com.hermanowicz.mypantry.navigation.features.newProduct.state

import com.hermanowicz.mypantry.data.local.model.ProductEntity

data class NewProductModel(
    val products: List<ProductEntity> = emptyList()
)
