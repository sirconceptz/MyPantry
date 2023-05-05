package com.hermanowicz.pantry.data.model

import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType

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
    val taste: String = ""
)
