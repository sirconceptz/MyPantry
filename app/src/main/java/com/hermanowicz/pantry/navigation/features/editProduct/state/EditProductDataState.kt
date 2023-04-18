package com.hermanowicz.pantry.navigation.features.editProduct.state

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.category.MainCategoriesTypes
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes

data class EditProductDataState(
    val name: String = "",
    var showMainCategoryDropdown: Boolean = false,
    var showDetailCategoryDropdown: Boolean = false,
    var expirationDatePickerData: DatePickerData = DatePickerData(),
    var productionDatePickerData: DatePickerData = DatePickerData(),
    var ownCategories: List<Category> = emptyList(),
    var mainCategory: String = MainCategoriesTypes.CHOOSE.name,
    val detailCategory: String = ChooseCategoryTypes.CHOOSE.name,
    val newQuantity: String = "",
    val oldQuantity: String = "0",
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
    val barcode: String = "",
    var showErrorWrongName: Boolean = false,
    var showErrorWrongQuantity: Boolean = false,
    var onNavigateBack: Boolean = false,
    val productsIdList: List<Int> = emptyList()
)
