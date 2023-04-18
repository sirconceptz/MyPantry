package com.hermanowicz.pantry.data.mapper

import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.data.model.StorageLocation

fun StorageLocationEntity.toDomainModel() = StorageLocation(
    id = id,
    name = name,
    description = description
)

fun StorageLocation.toEntityModel() = StorageLocationEntity(
    id = id,
    name = name,
    description = description
)
