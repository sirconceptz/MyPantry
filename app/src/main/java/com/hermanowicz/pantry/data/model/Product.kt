package com.hermanowicz.pantry.data.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val hashCode: String = "",
    val mainCategory: String = "",
    val detailCategory: String = "",
    val storageLocation: String = "",
    val expirationDate: String = "",
    val productionDate: String = "",
    val composition: String = "",
    val healingProperties: String = "",
    val dosage: String = "",
    val volume: Int = 0,
    val weight: Int = 0,
    val hasSugar: Boolean = false,
    val hasSalt: Boolean = false,
    val isVege: Boolean = false,
    val isBio: Boolean = false,
    val taste: String = "",
    val photoName: String = "",
    val barcode: String = ""
)
