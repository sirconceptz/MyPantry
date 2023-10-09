package com.hermanowicz.pantry.navigation.features.newProduct.state

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.ProductState
import com.hermanowicz.pantry.utils.enums.category.MainCategories
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.storageLocations.StorageLocations

data class NewProductState(
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
    override val quantity: String = "",
    override val storageLocation: String = StorageLocations.CHOOSE.name,
    override val expirationDate: String = "",
    override val productionDate: String = "",
    override val composition: String = "",
    override val healingProperties: String = "",
    override val dosage: String = "",
    override val volume: String = "",
    override val weight: String = "",
    override var hasSugar: Boolean = false,
    override var hasSalt: Boolean = false,
    override var isVege: Boolean = false,
    override var isBio: Boolean = false,
    override var showErrorWrongName: Boolean = false,
    override var showErrorWrongQuantity: Boolean = false,
    override val taste: String = "",
    val photoName: String = "",
    val photoDescription: String = "",
    var barcode: String = "",
    var showNavigateToPrintQRCodesDialog: Boolean = false,
    var showDialogMoreThanOneProductWithBarcode: Boolean = false,
    var groupProductsWithBarcode: List<GroupProduct> = emptyList(),
    var showDropdownChooseNewProduct: Boolean = false,
    var onNavigateToMyPantry: Boolean = false,
    var onNavigateToPrintQRCodes: Boolean = false,
    var productIdList: List<Long> = emptyList(),
    var selectedProductName: String = ""
) : ProductState
