package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.data.model.FilterProduct
import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.domain.GetDetailsCategoriesUseCase
import com.hermanowicz.mypantry.domain.GetGroupProductListUseCase
import com.hermanowicz.mypantry.domain.GetMainCategoriesUseCase
import com.hermanowicz.mypantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.mypantry.navigation.features.filterProduct.state.FilterProductDataState
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.mypantry.utils.CheckboxValueType
import com.hermanowicz.mypantry.utils.DateAndTimeConverter
import com.hermanowicz.mypantry.utils.DatePickerData
import com.hermanowicz.mypantry.utils.RegexFormats
import com.hermanowicz.mypantry.utils.category.MainCategoriesTypes
import com.hermanowicz.mypantry.utils.category.detailCategory.ChooseCategoryTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class MyPantryViewModel @Inject constructor(
    val getGroupProductListUseCase: GetGroupProductListUseCase,
    val observeAllProductsUseCase: ObserveAllProductsUseCase,
    val getMainCategoriesUseCase: GetMainCategoriesUseCase,
    val getDetailsCategoriesUseCase: GetDetailsCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MyPantryProductsUiState>(MyPantryProductsUiState.Empty)
    val uiState: StateFlow<MyPantryProductsUiState> = _uiState

    private val _filterProductDataState = MutableStateFlow(FilterProductDataState())
    var filterProductDataState: StateFlow<FilterProductDataState> =
        _filterProductDataState.asStateFlow()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        _uiState.value = MyPantryProductsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                observeAllProductsUseCase().map { products ->
                    filteredProductList(products, filterProductDataState.value.filterProduct)
                }.collect { products ->
                    _uiState.value = MyPantryProductsUiState.Loaded(
                        MyPantryModel(
                            groupsProduct = getGroupProductListUseCase(products),
                            loadingVisible = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = MyPantryProductsUiState.Error(e.toString())
            }
        }
    }

    private fun isProductAttributesValid(
        productIsBio: Boolean,
        productIsVege: Boolean,
        productHasSugar: Boolean,
        productHasSalt: Boolean,
        filterProductIsBio: CheckboxValueType,
        filterProductIsVege: CheckboxValueType,
        filterProductHasSugar: CheckboxValueType,
        filterProductHasSalt: CheckboxValueType
    ): Boolean {
        return ((productIsBio && (filterProductIsBio == CheckboxValueType.YES)) || (!productIsBio && (filterProductIsBio == CheckboxValueType.NO)) || filterProductIsBio == CheckboxValueType.DISABLED) &&
            ((productIsVege && (filterProductIsVege == CheckboxValueType.YES)) || (!productIsVege && (filterProductIsVege == CheckboxValueType.NO)) || filterProductIsVege == CheckboxValueType.DISABLED) &&
            ((productHasSugar && (filterProductHasSugar == CheckboxValueType.YES)) || (!productHasSugar && (filterProductHasSugar == CheckboxValueType.NO)) || filterProductHasSugar == CheckboxValueType.DISABLED) &&
            ((productHasSalt && (filterProductHasSalt == CheckboxValueType.YES)) || (!productHasSalt && (filterProductHasSalt == CheckboxValueType.NO)) || filterProductHasSalt == CheckboxValueType.DISABLED)
    }

    private fun filteredProductList(
        products: List<Product>,
        filterProduct: FilterProduct
    ): List<Product> {
        val mutableProducts: MutableList<Product> = mutableListOf()
        val volumeMin = filterProduct.volumeMin ?: 0
        val volumeMax = filterProduct.volumeMax ?: Integer.MAX_VALUE
        val weightMin = filterProduct.weightMin ?: 0
        val weightMax = filterProduct.weightMax ?: Integer.MAX_VALUE
        products.forEach { product ->
            if (product.name.contains(filterProduct.name) &&
                isMainCategoryValid(product.mainCategory, filterProduct.mainCategory) &&
                isDetailCategoryValid(product.detailCategory, filterProduct.detailCategory) &&
                product.storageLocation.contains(filterProduct.storageLocation) &&
                product.composition.contains(filterProduct.composition) &&
                product.healingProperties.contains(filterProduct.healingProperties) &&
                product.dosage.contains(filterProduct.dosage) &&
                product.volume in volumeMin..volumeMax &&
                product.weight in weightMin..weightMax &&
                isProductAttributesValid(
                        product.isBio,
                        product.isVege,
                        product.hasSugar,
                        product.hasSalt,
                        filterProduct.isBio,
                        filterProduct.isVege,
                        filterProduct.hasSugar,
                        filterProduct.hasSalt
                    ) &&
                product.taste.contains(filterProduct.taste) &&
                isExpirationDateInRange(
                        product.expirationDate,
                        filterProduct.expirationDateMin,
                        filterProduct.expirationDateMax
                    ) && isProductionDateInRange(
                        product.productionDate,
                        filterProduct.productionDateMin,
                        filterProduct.productionDateMax
                    )
            )
                mutableProducts.add(product)
        }
        return mutableProducts.toList()
    }

    private fun isMainCategoryValid(
        productMainCategory: String,
        filterProductMainCategory: String
    ): Boolean {
        return filterProductMainCategory == MainCategoriesTypes.CHOOSE.name ||
            filterProductMainCategory.isEmpty() ||
            productMainCategory == filterProductMainCategory ||
            productMainCategory.isEmpty()
    }

    private fun isDetailCategoryValid(
        productDetailCategory: String,
        filterProductDetailCategory: String
    ): Boolean {
        return filterProductDetailCategory == ChooseCategoryTypes.CHOOSE.name ||
            filterProductDetailCategory.isEmpty() ||
            productDetailCategory == filterProductDetailCategory ||
            productDetailCategory.isEmpty()
    }

    private fun isExpirationDateInRange(
        productExpirationDate: String,
        filterExpirationDateSince: String,
        filterExpirationDateFor: String
    ): Boolean {
        val isExpirationDateSinceValid =
            isDateAfter(productExpirationDate, filterExpirationDateSince)
        val isExpirationDateForValid = isDateAfter(filterExpirationDateFor, productExpirationDate)
        return isExpirationDateSinceValid && isExpirationDateForValid
    }

    private fun isProductionDateInRange(
        productProductionDate: String,
        filterProductionDateSince: String,
        filterProductionDateFor: String
    ): Boolean {
        val isProductionDateSinceValid =
            isDateAfter(productProductionDate, filterProductionDateSince)
        val isProductionDateForValid = isDateAfter(filterProductionDateFor, productProductionDate)
        return isProductionDateSinceValid && isProductionDateForValid
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateAfter(productDateString: String, filterProductDateString: String): Boolean {
        var isDateAfter = true
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        if (productDateString.isNotEmpty() && filterProductDateString.isNotEmpty()) {
            val productDate = sdf.parse(productDateString)
            val filterProductDate = sdf.parse(filterProductDateString)
            if (productDate != null) {
                isDateAfter = productDate.after(filterProductDate)
            }
        }
        return isDateAfter
    }

    fun onCleanClick() {
        _filterProductDataState.update { it.copy(filterProduct = FilterProduct()) }
    }

    fun onSearchClick() {
        _filterProductDataState.update {
            it.copy(
                filterProduct = FilterProduct(
                    name = filterProductDataState.value.name,
                    mainCategory = filterProductDataState.value.mainCategory,
                    detailCategory = filterProductDataState.value.detailCategory,
                    storageLocation = filterProductDataState.value.storageLocation,
                    expirationDateMin = filterProductDataState.value.expirationDateMin,
                    expirationDateMax = filterProductDataState.value.expirationDateMax,
                    productionDateMin = filterProductDataState.value.productionDateMin,
                    productionDateMax = filterProductDataState.value.productionDateMax,
                    composition = filterProductDataState.value.composition,
                    healingProperties = filterProductDataState.value.healingProperties,
                    dosage = filterProductDataState.value.dosage,
                    volumeMin = filterProductDataState.value.volumeMin.toIntOrNull(),
                    volumeMax = filterProductDataState.value.volumeMax.toIntOrNull(),
                    weightMin = filterProductDataState.value.weightMin.toIntOrNull(),
                    weightMax = filterProductDataState.value.weightMax.toIntOrNull(),
                    hasSugar = filterProductDataState.value.hasSugarValue,
                    hasSalt = filterProductDataState.value.hasSaltValue,
                    isBio = filterProductDataState.value.isBioValue,
                    isVege = filterProductDataState.value.isVegeValue,
                    taste = filterProductDataState.value.taste
                )
            )
        }
        fetchProducts()
        onNavigateBack(true)
    }

    fun onNavigateBack(bool: Boolean) {
        _filterProductDataState.update { it.copy(onNavigateBack = bool) }
    }

    fun onFilterNameChange(name: String) {
        _filterProductDataState.update { it.copy(name = name) }
    }

    fun onFilterExpirationDateMinChange(expirationDatePickerData: DatePickerData) {
        _filterProductDataState.update {
            it.copy(
                expirationDateMinPickerData = expirationDatePickerData,
                expirationDateMin = DateAndTimeConverter.getDateToDbFromDatePickerData(
                    expirationDatePickerData
                )
            )
        }
    }

    fun onFilterExpirationDateMaxChange(expirationDatePickerData: DatePickerData) {
        _filterProductDataState.update {
            it.copy(
                expirationDateMaxPickerData = expirationDatePickerData,
                expirationDateMax = DateAndTimeConverter.getDateToDbFromDatePickerData(
                    expirationDatePickerData
                )
            )
        }
    }

    fun onFilterProductionDateMinChange(productionDatePickerData: DatePickerData) {
        _filterProductDataState.update {
            it.copy(
                productionDateMinPickerData = productionDatePickerData,
                productionDateMin = DateAndTimeConverter.getDateToDbFromDatePickerData(
                    productionDatePickerData
                )
            )
        }
    }

    fun onFilterProductionDateMaxChange(productionDatePickerData: DatePickerData) {
        _filterProductDataState.update {
            it.copy(
                productionDateMaxPickerData = productionDatePickerData,
                productionDateMax = DateAndTimeConverter.getDateToDbFromDatePickerData(
                    productionDatePickerData
                )
            )
        }
    }

    fun onFilterCompositionChange(composition: String) {
        _filterProductDataState.update { it.copy(composition = composition) }
    }

    fun onFilterHealingPropertiesChange(healingProperties: String) {
        _filterProductDataState.update { it.copy(healingProperties = healingProperties) }
    }

    fun onFilterDosageChange(dosage: String) {
        _filterProductDataState.update { it.copy(dosage = dosage) }
    }

    fun onFilterWeightMinChange(weight: String) {
        if (weight.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.weightMin.isEmpty()) _filterProductDataState.update {
            it.copy(
                weightMin = weight
            )
        }
    }

    fun onFilterWeightMaxChange(weight: String) {
        if (weight.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.weightMax.isEmpty()) _filterProductDataState.update {
            it.copy(
                weightMax = weight
            )
        }
    }

    fun onFilterVolumeMinChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.volumeMin.isEmpty()) _filterProductDataState.update {
            it.copy(
                volumeMin = volume
            )
        }
    }

    fun onFilterVolumeMaxChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.volumeMax.isEmpty()) _filterProductDataState.update {
            it.copy(
                volumeMax = volume
            )
        }
    }

    fun onFilterIsVegeChange(isVege: Boolean) {
        val checkboxValue = if (isVege)
            CheckboxValueType.YES
        else
            CheckboxValueType.NO

        _filterProductDataState.update {
            it.copy(
                isVegeDisplayed = isVege,
                isVegeValue = checkboxValue
            )
        }
    }

    fun onFilterIsBioChange(isBio: Boolean) {
        val checkboxValue = if (isBio)
            CheckboxValueType.YES
        else
            CheckboxValueType.NO

        _filterProductDataState.update {
            it.copy(
                isBioDisplayed = isBio,
                isBioValue = checkboxValue
            )
        }
    }

    fun onFilterHasSugarChange(hasSugar: Boolean) {
        val checkboxValue = if (hasSugar)
            CheckboxValueType.YES
        else
            CheckboxValueType.NO

        _filterProductDataState.update {
            it.copy(
                hasSugarDisplayed = hasSugar,
                hasSugarValue = checkboxValue
            )
        }
    }

    fun onFilterHasSaltChange(hasSalt: Boolean) {
        val checkboxValue = if (hasSalt)
            CheckboxValueType.YES
        else
            CheckboxValueType.NO

        _filterProductDataState.update {
            it.copy(
                hasSaltDisplayed = hasSalt,
                hasSaltValue = checkboxValue
            )
        }
    }

    fun showFilterMainCategoryDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showMainCategoryDropdown = show) }
    }

    fun showFilterDetailCategoryDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showDetailCategoryDropdown = show) }
    }

    fun onFilterMainCategoryChange(mainCategory: String) {
        _filterProductDataState.update {
            it.copy(
                mainCategory = mainCategory, showMainCategoryDropdown = false
            )
        }
    }

    fun onFilterDetailCategoryChange(detailCategory: String) {
        _filterProductDataState.update {
            it.copy(
                detailCategory = detailCategory, showDetailCategoryDropdown = false
            )
        }
    }

    fun getFilterMainCategories(): Map<String, String> {
        return getMainCategoriesUseCase()
    }

    fun getFilterDetailCategories(): Map<String, String> {
        return getDetailsCategoriesUseCase(
            filterProductDataState.value.ownCategories, filterProductDataState.value.mainCategory
        )
    }
}
