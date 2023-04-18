package com.hermanowicz.pantry.data.model

data class GroupProduct(
    val product: Product = Product(),
    val quantity: Int = 0,
    val idList: MutableList<Int> = mutableListOf()
)
