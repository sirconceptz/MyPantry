package com.hermanowicz.pantry.data.mapper

import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.data.model.Category

fun CategoryEntity.toDomainModel() = Category(
    id = id,
    name = name,
    description = description
)

fun Category.toEntityModel() = CategoryEntity(
    id = id,
    name = name,
    description = description
)
