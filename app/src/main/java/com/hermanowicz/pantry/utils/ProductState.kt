package com.hermanowicz.pantry.utils

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.StorageLocation

interface ProductState {
    val name: String
    var productionDatePickerData: DatePickerData
    var expirationDatePickerData: DatePickerData
    var ownCategories: List<Category>
    var storageLocations: List<StorageLocation>
    var mainCategory: String
    var detailCategory: String
    val storageLocation: String
    val expirationDate: String
    val productionDate: String
    val composition: String
    val healingProperties: String
    val quantity: String
    val dosage: String
    val volume: String
    val weight: String
    val isVege: Boolean
    val isBio: Boolean
    val hasSugar: Boolean
    val hasSalt: Boolean
    val taste: String
    var showErrorWrongName: Boolean
    var showErrorWrongQuantity: Boolean
    var showMainCategoryDropdown: Boolean
    var showDetailCategoryDropdown: Boolean
    var showStorageLocationDropdown: Boolean
}
