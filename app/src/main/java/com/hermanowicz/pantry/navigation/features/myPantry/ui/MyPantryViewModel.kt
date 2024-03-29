package com.hermanowicz.pantry.navigation.features.myPantry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.FilterProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.data.model.errorAlertSystem.ErrorAlert
import com.hermanowicz.pantry.domain.category.GetDetailCategoriesUseCase
import com.hermanowicz.pantry.domain.category.GetMainCategoriesUseCase
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.errorAlertSystem.CheckIsErrorWasDisplayedUseCase
import com.hermanowicz.pantry.domain.errorAlertSystem.FetchActiveErrorAlertsUseCase
import com.hermanowicz.pantry.domain.errorAlertSystem.SaveErrorAsDisplayedUseCase
import com.hermanowicz.pantry.domain.product.GetFilteredProductListUseCase
import com.hermanowicz.pantry.domain.product.GetGroupProductListUseCase
import com.hermanowicz.pantry.domain.product.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.storageLocation.GetStorageLocationsMapUseCase
import com.hermanowicz.pantry.domain.storageLocation.ObserveAllStorageLocationsUseCase
import com.hermanowicz.pantry.navigation.features.filterProduct.state.FilterProductDataState
import com.hermanowicz.pantry.navigation.features.myPantry.state.ErrorAlertSystemState
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.RegexFormats
import com.hermanowicz.pantry.utils.enums.Taste
import com.hermanowicz.pantry.utils.enums.storageLocations.StorageLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPantryViewModel @Inject constructor(
    private val getGroupProductListUseCase: GetGroupProductListUseCase,
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase,
    private val observeAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase,
    private val getMainCategoriesUseCase: GetMainCategoriesUseCase,
    private val getDetailCategoriesUseCase: GetDetailCategoriesUseCase,
    private val getStorageLocationsMapUseCase: GetStorageLocationsMapUseCase,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase,
    private val getFilteredProductListUseCase: GetFilteredProductListUseCase,
    private val fetchActiveErrorAlertsUseCase: FetchActiveErrorAlertsUseCase,
    private val checkIsErrorWasDisplayedUseCase: CheckIsErrorWasDisplayedUseCase,
    private val saveErrorAsDisplayedUseCase: SaveErrorAsDisplayedUseCase
) : ViewModel() {
    private val _errorAlertSystemState = MutableStateFlow(ErrorAlertSystemState())
    val errorAlertSystemState: StateFlow<ErrorAlertSystemState> = _errorAlertSystemState

    private val _filterProductDataState = MutableStateFlow(FilterProductDataState())
    var filterProductDataState: StateFlow<FilterProductDataState> =
        _filterProductDataState.asStateFlow()

    private lateinit var products: List<Product>

    init {
        enableErrorAlertSystem()
        fetchOwnCategoriesAndStorageLocations()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<MyPantryProductsUiState> = observeDatabaseModeUseCase()
        .flatMapLatest { databaseMode ->
            observeAllProductsUseCase(databaseMode)
        }
        .flatMapLatest {
            products = it
            filterProductDataState
        }
        .map { filterProductDataState ->
            val filteredProductList = getFilteredProductListUseCase(
                products,
                filterProductDataState.filterProduct
            )
            MyPantryProductsUiState.Loaded(
                MyPantryModel(
                    groupProductList = getGroupProductListUseCase(filteredProductList),
                    loadingVisible = false
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MyPantryProductsUiState.Loading
        )

    fun fetchOwnCategoriesAndStorageLocations() {
        viewModelScope.launch {
            observeDatabaseModeUseCase().collect { databaseMode ->
                observeAllOwnCategoriesUseCase(databaseMode).collect { ownCategories ->
                    updateOwnCategoriesState(ownCategories)
                    observeAllStorageLocationsUseCase(databaseMode).collect { storageLocations ->
                        updateStorageLocationState(storageLocations)
                    }
                }
            }
        }
    }

    private fun updateStorageLocationState(storageLocations: List<StorageLocation>) {
        _filterProductDataState.update {
            it.copy(
                storageLocations = storageLocations
            )
        }
    }

    private fun updateOwnCategoriesState(ownCategories: List<Category>) {
        _filterProductDataState.update {
            it.copy(
                ownCategories = ownCategories
            )
        }
    }

    fun onCleanClick() {
        _filterProductDataState.update {
            it.copy(
                filterProduct = FilterProduct(),
                sweet = false,
                sweetAndSour = false,
                sour = false,
                salty = false,
                bitter = false,
                spicy = false
            )
        }
    }

    fun onSearchClick() {
        var storageLocation = ""
        if (filterProductDataState.value.storageLocation != StorageLocations.CHOOSE.name) {
            storageLocation = filterProductDataState.value.storageLocation
        }
        _filterProductDataState.update {
            it.copy(
                filterProduct = FilterProduct(
                    name = filterProductDataState.value.name,
                    mainCategory = filterProductDataState.value.mainCategory,
                    detailCategory = filterProductDataState.value.detailCategory,
                    storageLocation = storageLocation,
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
                    hasSugar = filterProductDataState.value.hasSugar,
                    hasSalt = filterProductDataState.value.hasSalt,
                    isBio = filterProductDataState.value.isBio,
                    isVege = filterProductDataState.value.isVege,
                    sweet = filterProductDataState.value.sweet,
                    sour = filterProductDataState.value.sour,
                    sweetAndSour = filterProductDataState.value.sweetAndSour,
                    salty = filterProductDataState.value.salty,
                    spicy = filterProductDataState.value.spicy
                )
            )
        }
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
        if (weight.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.weightMin.isEmpty()) {
            _filterProductDataState.update {
                it.copy(
                    weightMin = weight
                )
            }
        }
    }

    fun onFilterWeightMaxChange(weight: String) {
        if (weight.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.weightMax.isEmpty()) {
            _filterProductDataState.update {
                it.copy(
                    weightMax = weight
                )
            }
        }
    }

    fun onFilterVolumeMinChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.volumeMin.isEmpty()) {
            _filterProductDataState.update {
                it.copy(
                    volumeMin = volume
                )
            }
        }
    }

    fun onFilterVolumeMaxChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex) || filterProductDataState.value.volumeMax.isEmpty()) {
            _filterProductDataState.update {
                it.copy(
                    volumeMax = volume
                )
            }
        }
    }

    fun onFilterIsVegeChange(isVege: String) {
        _filterProductDataState.update {
            it.copy(
                isVege = isVege,
                showIsVegeDropdown = false
            )
        }
    }

    fun showFilterIsVegeDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showIsVegeDropdown = show) }
    }

    fun onFilterIsBioChange(isBio: String) {
        _filterProductDataState.update {
            it.copy(
                isBio = isBio,
                showIsBioDropdown = false
            )
        }
    }

    fun showFilterIsBioDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showIsBioDropdown = show) }
    }

    fun onFilterHasSugarChange(hasSugar: String) {
        _filterProductDataState.update {
            it.copy(
                hasSugar = hasSugar,
                showHasSugarDropdown = false
            )
        }
    }

    fun showFilterHasSugarDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showHasSugarDropdown = show) }
    }

    fun onFilterHasSaltChange(hasSalt: String) {
        _filterProductDataState.update {
            it.copy(
                hasSalt = hasSalt,
                showHasSaltDropdown = false
            )
        }
    }

    fun showStorageLocationDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showStorageLocationDropdown = show) }
    }

    fun showFilterHasSaltDropdown(show: Boolean) {
        _filterProductDataState.update { it.copy(showHasSaltDropdown = show) }
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
                mainCategory = mainCategory,
                showMainCategoryDropdown = false
            )
        }
    }

    fun onFilterDetailCategoryChange(detailCategory: String) {
        _filterProductDataState.update {
            it.copy(
                detailCategory = detailCategory,
                showDetailCategoryDropdown = false
            )
        }
    }

    fun onFilterStorageLocationChange(storageLocation: String) {
        _filterProductDataState.update {
            it.copy(
                storageLocation = storageLocation
            )
        }
    }

    fun getFilterMainCategories(): Map<String, String> {
        return getMainCategoriesUseCase()
    }

    fun getFilterDetailCategories(): Map<String, String> {
        return getDetailCategoriesUseCase(
            filterProductDataState.value.ownCategories,
            filterProductDataState.value.mainCategory
        )
    }

    fun getStorageLocations(): Map<String, String> {
        return getStorageLocationsMapUseCase(
            filterProductDataState.value.storageLocations
        )
    }

    fun onFilterTasteSelect(taste: String, bool: Boolean) {
        when (taste) {
            Taste.SWEET.name -> {
                _filterProductDataState.update {
                    it.copy(
                        sweet = bool
                    )
                }
            }

            Taste.SOUR.name -> {
                _filterProductDataState.update {
                    it.copy(
                        sour = bool
                    )
                }
            }

            Taste.SWEET_AND_SOUR.name -> {
                _filterProductDataState.update {
                    it.copy(
                        sweetAndSour = bool
                    )
                }
            }

            Taste.BITTER.name -> {
                _filterProductDataState.update {
                    it.copy(
                        bitter = bool
                    )
                }
            }

            Taste.SALTY.name -> {
                _filterProductDataState.update {
                    it.copy(
                        salty = bool
                    )
                }
            }

            Taste.SPICY.name -> {
                _filterProductDataState.update {
                    it.copy(
                        spicy = bool
                    )
                }
            }
        }
    }

    private fun enableErrorAlertSystem() {
        viewModelScope.launch(Dispatchers.IO) {
            val errorList = fetchActiveErrorAlertsUseCase()
            errorList.forEach { error ->
                val displayed = checkIsErrorWasDisplayedUseCase(error.title)
                if (!displayed) {
                    val newList = errorAlertSystemState.value.activeErrorList.toMutableList()
                    newList.add(error)
                    _errorAlertSystemState.update {
                        it.copy(
                            activeErrorList = newList
                        )
                    }
                }
            }
        }
    }

    fun saveErrorAsDisplayedAndCloseDialog(error: ErrorAlert) {
        viewModelScope.launch(Dispatchers.IO) {
            saveErrorAsDisplayedUseCase(error.errorCode)
            val newList = errorAlertSystemState.value.activeErrorList.toMutableList()
            newList.remove(error)
            _errorAlertSystemState.update {
                it.copy(
                    activeErrorList = newList
                )
            }
        }
    }
}
