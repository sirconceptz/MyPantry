package com.hermanowicz.mypantry.data.model

import com.hermanowicz.mypantry.data.local.model.ProductEntity

data class GroupProduct(
    val product: ProductEntity,
    val quantity: Int
)
