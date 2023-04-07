package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.domain.GetDetailsCategoriesUseCase
import com.hermanowicz.mypantry.domain.GetGroupProductListUseCase
import com.hermanowicz.mypantry.domain.GetMainCategoriesUseCase
import com.hermanowicz.mypantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.mypantry.navigation.features.filterProduct.state.FilterProductDataState
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.mypantry.utils.DateAndTimeConverter
import com.hermanowicz.mypantry.utils.DatePickerData
import com.hermanowicz.mypantry.utils.RegexFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                observeAllProductsUseCase().collect { products ->
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

    fun onSearchClick() {
    }

    fun onNavigateToMyPantry(bool: Boolean) {
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
        if (weight.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.weightMin.isEmpty())
            _filterProductDataState.update {
                it.copy(
                    weightMin = weight
                )
            }
    }

    fun onFilterWeightMaxChange(weight: String) {
        if (weight.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.weightMax.isEmpty())
            _filterProductDataState.update {
                it.copy(
                    weightMax = weight
                )
            }
    }

    fun onFilterVolumeMinChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.volumeMin.isEmpty())
            _filterProductDataState.update {
                it.copy(
                    volumeMin = volume
                )
            }
    }

    fun onFilterVolumeMaxChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.volumeMax.isEmpty())
            _filterProductDataState.update {
                it.copy(
                    volumeMax = volume
                )
            }
    }

    fun onFilterIsVegeChange(isVege: Boolean) {
        _filterProductDataState.update { it.copy(isVege = isVege) }
    }

    fun onFilterIsBioChange(isBio: Boolean) {
        _filterProductDataState.update { it.copy(isBio = isBio) }
    }

    fun onFilterHasSugarChange(hasSugar: Boolean) {
        _filterProductDataState.update { it.copy(hasSugar = hasSugar) }
    }

    fun onFilterHasSaltChange(hasSalt: Boolean) {
        _filterProductDataState.update { it.copy(hasSalt = hasSalt) }
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
