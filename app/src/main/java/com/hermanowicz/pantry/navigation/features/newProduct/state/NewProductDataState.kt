package com.hermanowicz.pantry.navigation.features.newProduct.state

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes

data class NewProductDataState(
    val name: String = "",
    var showMainCategoryDropdown: Boolean = false,
    var showDetailCategoryDropdown: Boolean = false,
    var expirationDatePickerData: DatePickerData = DatePickerData(),
    var productionDatePickerData: DatePickerData = DatePickerData(),
    var ownCategories: List<Category> = emptyList(),
    var mainCategory: String = MainCategories.CHOOSE.name,
    val detailCategory: String = ChooseCategoryTypes.CHOOSE.name,
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
    val barcode: String = "",
    var showErrorWrongName: Boolean = false,
    var showErrorWrongQuantity: Boolean = false,
    var showNavigateToPrintQRCodesDialog: Boolean = false,
    var onNavigateToMyPantry: Boolean = false,
    var onNavigateToPrintQRCodes: Boolean = false,
    var productIdList: List<Long> = emptyList()
)
