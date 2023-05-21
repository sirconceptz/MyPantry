package com.hermanowicz.pantry.data.mapper

import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.data.model.Product

fun ProductEntity.toDomainModel() = Product(
    id = id,
    name = name,
    hashCode = hashCode,
    mainCategory = mainCategory,
    detailCategory = detailCategory,
    storageLocation = storageLocation,
    expirationDate = expirationDate,
    productionDate = productionDate,
    composition = composition,
    healingProperties = healingProperties,
    dosage = dosage,
    volume = volume,
    weight = weight,
    hasSugar = hasSugar,
    hasSalt = hasSalt,
    isVege = isVege,
    isBio = isBio,
    taste = taste,
    photoName = photoName,
    barcode = barcode
)

fun Product.toEntityModel() = ProductEntity(
    id = id,
    name = name,
    hashCode = hashCode,
    mainCategory = mainCategory,
    detailCategory = detailCategory,
    storageLocation = storageLocation,
    expirationDate = expirationDate,
    productionDate = productionDate,
    composition = composition,
    healingProperties = healingProperties,
    dosage = dosage,
    volume = volume,
    weight = weight,
    hasSugar = hasSugar,
    hasSalt = hasSalt,
    isVege = isVege,
    isBio = isBio,
    taste = taste,
    photoName = photoName,
    barcode = barcode
)
