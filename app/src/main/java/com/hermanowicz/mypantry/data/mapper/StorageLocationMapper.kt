package com.hermanowicz.mypantry.data.mapper

import com.hermanowicz.mypantry.data.local.model.StorageLocationEntity
import com.hermanowicz.mypantry.data.model.StorageLocation

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
