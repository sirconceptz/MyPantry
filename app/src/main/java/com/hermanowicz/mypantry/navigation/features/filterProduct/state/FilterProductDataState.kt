package com.hermanowicz.mypantry.navigation.features.filterProduct.state

import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.data.model.FilterProduct
import com.hermanowicz.mypantry.utils.CheckboxValueType
import com.hermanowicz.mypantry.utils.DatePickerData
import com.hermanowicz.mypantry.utils.category.MainCategoriesTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.ChooseCategoryTypes

data class FilterProductDataState(
    val name: String = "",
    var expirationDateMinPickerData: DatePickerData = DatePickerData(),
    var expirationDateMaxPickerData: DatePickerData = DatePickerData(),
    var productionDateMinPickerData: DatePickerData = DatePickerData(),
    var productionDateMaxPickerData: DatePickerData = DatePickerData(),
    var ownCategories: List<Category> = emptyList(),
    var mainCategory: String = MainCategoriesTypes.CHOOSE.name,
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
    val hasSugarDisplayed: Boolean = false,
    val hasSaltDisplayed: Boolean = false,
    val isVegeDisplayed: Boolean = false,
    val isBioDisplayed: Boolean = false,
    val hasSugarValue: CheckboxValueType = CheckboxValueType.DISABLED,
    val hasSaltValue: CheckboxValueType = CheckboxValueType.DISABLED,
    val isVegeValue: CheckboxValueType = CheckboxValueType.DISABLED,
    val isBioValue: CheckboxValueType = CheckboxValueType.DISABLED,
    val taste: String = "",
    var showErrorWrongName: Boolean = false,
    var showMainCategoryDropdown: Boolean = false,
    var showDetailCategoryDropdown: Boolean = false,
    var onNavigateBack: Boolean = false,
    val productsIdList: List<Int> = emptyList(),
    var filterProduct: FilterProduct = FilterProduct()
)
