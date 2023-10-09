package com.hermanowicz.pantry.navigation.features.editProduct.state

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.ProductState
import com.hermanowicz.pantry.utils.enums.category.MainCategories
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.storageLocations.StorageLocations

data class EditProductDataState(
    override val name: String = "",
    override var showMainCategoryDropdown: Boolean = false,
    override var showDetailCategoryDropdown: Boolean = false,
    override var showStorageLocationDropdown: Boolean = false,
    override var expirationDatePickerData: DatePickerData = DatePickerData(),
    override var productionDatePickerData: DatePickerData = DatePickerData(),
    override var ownCategories: List<Category> = emptyList(),
    override var storageLocations: List<StorageLocation> = emptyList(),
    override var mainCategory: String = MainCategories.CHOOSE.name,
    override var detailCategory: String = ChooseCategoryTypes.CHOOSE.name,
    override val storageLocation: String = StorageLocations.CHOOSE.name,
    val newQuantity: String = "",
    override val quantity: String = "0",
    override val expirationDate: String = "",
    override val productionDate: String = "",
    override val composition: String = "",
    override val healingProperties: String = "",
    override val dosage: String = "",
    override val volume: String = "",
    override val weight: String = "",
    override val hasSugar: Boolean = false,
    override val hasSalt: Boolean = false,
    override val isVege: Boolean = false,
    override val isBio: Boolean = false,
    override val taste: String = "",
    override var showErrorWrongName: Boolean = false,
    override var showErrorWrongQuantity: Boolean = false,
    val photoName: String = "",
    val photoDescription: String = "",
    val barcode: String = "",
    val hashCode: String = "",
    var onNavigateToMyPantry: Boolean = false,
    val productsIdList: List<Int> = emptyList()
) : ProductState
