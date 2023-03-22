package com.hermanowicz.mypantry.utils

data class ProductDataState(
    val name: String = "",
    val mainCategory: String = "",
    val detailCategory: String = "",
    val quantity: String = "",
    val storageLocation: String = "",
    val expirationDate: String = "",
    val productionDate: String = "",
    val composition: String = "",
    val healingProperties: String = "",
    val dosage: String = "",
    val volume: String = "",
    val weight: String = "",
    val hasSugar: Boolean = false,
    val hasSalt: Boolean = false,
    val isVege: Boolean = false,
    val isBio: Boolean = false,
    val taste: String = "",
    val photoName: String = "",
    val photoDescription: String = "",
    val barcode: String = ""
)
