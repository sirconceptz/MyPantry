package com.hermanowicz.pantry.data.mapper

import com.hermanowicz.pantry.data.local.model.CategoriesEntity
import com.hermanowicz.pantry.data.model.Category

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
