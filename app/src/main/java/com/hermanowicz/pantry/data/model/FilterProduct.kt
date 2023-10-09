package com.hermanowicz.pantry.data.model

import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType
import com.hermanowicz.pantry.utils.enums.category.MainCategories
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes

data class FilterProduct(
    val name: String = "",
    val mainCategory: String = MainCategories.CHOOSE.name,
    val detailCategory: String = ChooseCategoryTypes.CHOOSE.name,
    val storageLocation: String = "",
    val expirationDateMin: String = "",
    val expirationDateMax: String = "",
    val productionDateMin: String = "",
    val productionDateMax: String = "",
    val composition: String = "",
    val healingProperties: String = "",
    val dosage: String = "",
    val volumeMin: Int? = null,
    val volumeMax: Int? = null,
    val weightMin: Int? = null,
    val weightMax: Int? = null,
    val hasSugar: String = ProductAttributesValueType.ALL.name,
    val hasSalt: String = ProductAttributesValueType.ALL.name,
    val isVege: String = ProductAttributesValueType.ALL.name,
    val isBio: String = ProductAttributesValueType.ALL.name,
    val sweet: Boolean = false,
    val sour: Boolean = false,
    val sweetAndSour: Boolean = false,
    val salty: Boolean = false,
    val spicy: Boolean = false
)
