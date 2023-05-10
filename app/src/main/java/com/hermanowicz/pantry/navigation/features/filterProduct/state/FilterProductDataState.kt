package com.hermanowicz.pantry.navigation.features.filterProduct.state

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.FilterProduct
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType

data class FilterProductDataState(
    val name: String = "",
    var expirationDateMinPickerData: DatePickerData = DatePickerData(),
    var expirationDateMaxPickerData: DatePickerData = DatePickerData(),
    var productionDateMinPickerData: DatePickerData = DatePickerData(),
    var productionDateMaxPickerData: DatePickerData = DatePickerData(),
    var ownCategories: List<Category> = emptyList(),
    var mainCategory: String = MainCategories.CHOOSE.name,
    var detailCategory: String = ChooseCategoryTypes.CHOOSE.name,
    val storageLocation: String = "",
    val expirationDateMin: String = "",
    val expirationDateMax: String = "",
    val productionDateMin: String = "",
    val productionDateMax: String = "",
    val composition: String = "",
    val healingProperties: String = "",
    val dosage: String = "",
    val volumeMin: String = "",
    val volumeMax: String = "",
    val weightMin: String = "",
    val weightMax: String = "",
    val isVege: String = ProductAttributesValueType.ALL.name,
    val isBio: String = ProductAttributesValueType.ALL.name,
    val hasSugar: String = ProductAttributesValueType.ALL.name,
    val hasSalt: String = ProductAttributesValueType.ALL.name,
    val sweet: Boolean = false,
    val sour: Boolean = false,
    val sweetAndSour: Boolean = false,
    val salty: Boolean = false,
    val spicy: Boolean = false,
    var showErrorWrongName: Boolean = false,
    var showMainCategoryDropdown: Boolean = false,
    var showDetailCategoryDropdown: Boolean = false,
    var showIsVegeDropdown: Boolean = false,
    var showIsBioDropdown: Boolean = false,
    var showHasSugarDropdown: Boolean = false,
    var showHasSaltDropdown: Boolean = false,
    var onNavigateBack: Boolean = false,
    val productsIdList: List<Int> = emptyList(),
    var filterProduct: FilterProduct = FilterProduct()
)
