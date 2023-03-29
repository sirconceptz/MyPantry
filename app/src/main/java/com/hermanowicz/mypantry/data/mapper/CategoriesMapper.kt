package com.hermanowicz.mypantry.data.mapper

import com.hermanowicz.mypantry.data.local.model.CategoriesEntity
import com.hermanowicz.mypantry.data.model.Category

fun CategoriesEntity.toDomainModel() = Category(
    id = id,
    name = name,
    description = description
)

fun Category.toEntityModel() = CategoriesEntity(
    id = id,
    name = name,
    description = description
)
