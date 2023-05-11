package com.hermanowicz.pantry.navigation.features.newProduct.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.CheckFormatIsNumberUseCase
import com.hermanowicz.pantry.domain.FetchDatabaseModeUseCase
import com.hermanowicz.pantry.domain.GetDetailsCategoriesUseCase
import com.hermanowicz.pantry.domain.GetGroupProductListByBarcodeUseCase
import com.hermanowicz.pantry.domain.GetMainCategoriesUseCase
import com.hermanowicz.pantry.domain.GetOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.SaveProductsUseCase
import com.hermanowicz.pantry.navigation.features.newProduct.state.NewProductState
import com.hermanowicz.pantry.navigation.features.newProduct.state.NewProductUiState
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.category.detailCategory.ChooseCategoryTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewProductViewModel @Inject constructor(
    private val saveProductsUseCase: SaveProductsUseCase,
    private val getMainCategoriesUseCase: GetMainCategoriesUseCase,
    private val getDetailCategoriesUseCase: GetDetailsCategoriesUseCase,
    private val getOwnCategoriesUseCase: GetOwnCategoriesUseCase,
    private val fetchDatabaseModeUseCase: FetchDatabaseModeUseCase,
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val getGroupProductListByBarcodeUseCase: GetGroupProductListByBarcodeUseCase,
    private val checkFormatIsNumberUseCase: CheckFormatIsNumberUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewProductUiState.Empty)
    var uiState: StateFlow<NewProductUiState> = _uiState.asStateFlow()

    private val _productDataState = MutableStateFlow(NewProductState())
    var productDataState: StateFlow<NewProductState> = _productDataState.asStateFlow()


    private val barcode: String = savedStateHandle["barcode"] ?: "0"

    init {
        fetchOwnCategories()
        if (barcode != "0")
            fetchProductData(barcode)
    }

    private fun fetchOwnCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _productDataState.update { it.copy(ownCategories = getOwnCategoriesUseCase()) }
        }
    }

    private fun fetchProductData(barcode: String) {
        viewModelScope.launch {
            fetchDatabaseModeUseCase().collect { databaseMode ->
                observeAllProductsUseCase(databaseMode).collect { products ->
                    val groupProducts = getGroupProductListByBarcodeUseCase(barcode, products, null)
                    if (groupProducts.size > 1) {
                        onShowDialogMoreThanOneProductWithBarcode(true, groupProducts)
                    } else if (groupProducts.size == 1) {
                        val groupProduct = groupProducts[0]
                        _productDataState.value = NewProductState(
                            name = groupProduct.product.name,
                            expirationDate = groupProduct.product.expirationDate,
                            productionDate = groupProduct.product.productionDate,
                            composition = groupProduct.product.composition,
                            quantity = groupProduct.quantity.toString(),
                            healingProperties = groupProduct.product.healingProperties,
                            dosage = groupProduct.product.dosage,
                            hasSugar = groupProduct.product.hasSugar,
                            hasSalt = groupProduct.product.hasSalt,
                            isVege = groupProduct.product.isVege,
                            isBio = groupProduct.product.isBio,
                            taste = groupProduct.product.taste,
                            weight = groupProduct.product.weight.toString(),
                            volume = groupProduct.product.volume.toString()
                        )
                    }
                }
            }
        }
    }

    private fun fetchProductData(barcode: String, productName: String) {
        viewModelScope.launch {
            fetchDatabaseModeUseCase().collect { databaseMode ->
                observeAllProductsUseCase(databaseMode).collect { products ->
                    val groupProducts =
                        getGroupProductListByBarcodeUseCase(barcode, products, productName)
                    if (groupProducts.size > 1) {
                        onShowDialogMoreThanOneProductWithBarcode(true, groupProducts)
                    } else if (groupProducts.size == 1) {
                        val groupProduct = groupProducts[0]
                        _productDataState.value = NewProductState(
                            name = groupProduct.product.name,
                            expirationDate = groupProduct.product.expirationDate,
                            productionDate = groupProduct.product.productionDate,
                            composition = groupProduct.product.composition,
                            quantity = groupProduct.quantity.toString(),
                            healingProperties = groupProduct.product.healingProperties,
                            dosage = groupProduct.product.dosage,
                            hasSugar = groupProduct.product.hasSugar,
                            hasSalt = groupProduct.product.hasSalt,
                            isVege = groupProduct.product.isVege,
                            isBio = groupProduct.product.isBio,
                            taste = groupProduct.product.taste,
                            weight = groupProduct.product.weight.toString(),
                            volume = groupProduct.product.volume.toString()
                        )
                    }
                }
            }
        }
    }

    fun onShowDialogMoreThanOneProductWithBarcode(
        bool: Boolean,
        groupProducts: List<GroupProduct>
    ) {
        _productDataState.update {
            it.copy(
                showDialogMoreThanOneProductWithBarcode = bool,
                groupProductsWithBarcode = groupProducts
            )
        }
        if (groupProducts.size > 1)
            _productDataState.update {
                it.copy(
                    selectedProductName = groupProducts[0].product.name
                )
            }
    }

    fun onSelectGroupProduct(productName: String) {
        _productDataState.update {
            it.copy(
                selectedProductName = productName,
                showDropdownChooseNewProduct = false
            )
        }
    }

    fun onSaveClick() {
        if (productDataState.value.name.length < 3 || productDataState.value.name.length > 40)
            _productDataState.update { it.copy(showErrorWrongName = true) }
        else {
            viewModelScope.launch(Dispatchers.IO) {
                val productIdList = saveProducts()
                _productDataState.update {
                    it.copy(
                        productIdList = productIdList
                    )
                }
                cleanErrors()
            }
            showNavigateToPrintQRCodesDialog(true)
        }
    }

    fun showNavigateToPrintQRCodesDialog(bool: Boolean) {
        _productDataState.update { it.copy(showNavigateToPrintQRCodesDialog = bool) }
    }

    fun onNavigateToPrintQRCodes(bool: Boolean) {
        _productDataState.update {
            it.copy(
                onNavigateToPrintQRCodes = bool,
                showNavigateToPrintQRCodesDialog = false
            )
        }
    }

    private fun cleanErrors() {
        _productDataState.update { it.copy(showErrorWrongName = false) }
    }

    private suspend fun saveProducts(): List<Long> {
        var mainCategory = ""
        var detailCategory = ""
        if (productDataState.value.mainCategory != MainCategories.CHOOSE.name)
            mainCategory = productDataState.value.mainCategory
        if (productDataState.value.detailCategory != MainCategories.CHOOSE.name)
            detailCategory = productDataState.value.detailCategory
        var product = Product(
            name = productDataState.value.name,
            mainCategory = mainCategory,
            detailCategory = detailCategory,
            expirationDate = productDataState.value.expirationDate,
            productionDate = productDataState.value.productionDate,
            composition = productDataState.value.composition,
            healingProperties = productDataState.value.healingProperties,
            dosage = productDataState.value.dosage,
            hasSugar = productDataState.value.hasSugar,
            hasSalt = productDataState.value.hasSalt,
            isVege = productDataState.value.isVege,
            isBio = productDataState.value.isBio,
            weight = productDataState.value.weight.toIntOrNull() ?: 0,
            volume = productDataState.value.volume.toIntOrNull() ?: 0,
            taste = productDataState.value.taste
        )
        product = product.copy(hashCode = product.hashCode().toString())
        val products: MutableList<Product> = mutableListOf()
        val quantity =
            if (productDataState.value.quantity.isEmpty()) 1
            else productDataState.value.quantity.toIntOrNull() ?: 1
        for (i in 1..quantity) {
            products.add(product)
        }
        return saveProductsUseCase(products)
    }

    fun getMainCategories(): Map<String, String> {
        return getMainCategoriesUseCase()
    }

    fun getDetailCategories(): Map<String, String> {
        return getDetailCategoriesUseCase(
            productDataState.value.ownCategories,
            productDataState.value.mainCategory
        )
    }

    fun onNameChange(name: String) {
        _productDataState.update { it.copy(name = name) }
    }

    fun onExpirationDateChange(expirationDatePickerData: DatePickerData) {
        _productDataState.update {
            it.copy(
                expirationDatePickerData = expirationDatePickerData,
                expirationDate = DateAndTimeConverter.getDateToDbFromDatePickerData(
                    expirationDatePickerData
                )
            )
        }
    }

    fun onProductionDateChange(productionDatePickerData: DatePickerData) {
        _productDataState.update {
            it.copy(
                productionDatePickerData = productionDatePickerData,
                productionDate = DateAndTimeConverter.getDateToDbFromDatePickerData(
                    productionDatePickerData
                )
            )
        }
    }

    fun onQuantityChange(quantity: String) {
        if (checkFormatIsNumberUseCase(quantity))
            _productDataState.update { it.copy(quantity = quantity) }
    }

    fun onCompositionChange(composition: String) {
        _productDataState.update { it.copy(composition = composition) }
    }

    fun onHealingPropertiesChange(healingProperties: String) {
        _productDataState.update { it.copy(healingProperties = healingProperties) }
    }

    fun onDosageChange(dosage: String) {
        _productDataState.update { it.copy(dosage = dosage) }
    }

    fun onWeightChange(weight: String) {
        if (checkFormatIsNumberUseCase(weight))
            _productDataState.update { it.copy(weight = weight) }
    }

    fun onVolumeChange(volume: String) {
        if (checkFormatIsNumberUseCase(volume))
            _productDataState.update { it.copy(volume = volume) }
    }

    fun onIsVegeChange(isVege: Boolean) {
        _productDataState.update { it.copy(isVege = isVege) }
    }

    fun onIsBioChange(isBio: Boolean) {
        _productDataState.update { it.copy(isBio = isBio) }
    }

    fun onHasSugarChange(hasSugar: Boolean) {
        _productDataState.update { it.copy(hasSugar = hasSugar) }
    }

    fun onHasSaltChange(hasSalt: Boolean) {
        _productDataState.update { it.copy(hasSalt = hasSalt) }
    }

    fun showMainCategoryDropdown(show: Boolean) {
        _productDataState.update { it.copy(showMainCategoryDropdown = show) }
    }

    fun showDetailCategoryDropdown(show: Boolean) {
        _productDataState.update { it.copy(showDetailCategoryDropdown = show) }
    }

    fun onMainCategoryChange(mainCategory: String) {
        _productDataState.update {
            it.copy(
                mainCategory = mainCategory,
                detailCategory = ChooseCategoryTypes.CHOOSE.name,
                showMainCategoryDropdown = false
            )
        }
    }

    fun onDetailCategoryChange(detailCategory: String) {
        _productDataState.update {
            it.copy(
                detailCategory = detailCategory,
                showDetailCategoryDropdown = false
            )
        }
    }

    fun onNavigateToMyPantry(bool: Boolean) {
        _productDataState.update {
            it.copy(
                onNavigateToMyPantry = bool
            )
        }
    }

    fun onTasteSelect(taste: String) {
        _productDataState.update {
            it.copy(
                taste = taste
            )
        }
    }

    fun showChooseNewProductDropdown(bool: Boolean) {
        _productDataState.update {
            it.copy(
                showDropdownChooseNewProduct = bool
            )
        }
    }

    fun onPositiveClickProductWithBarcodeDialog() {
        fetchProductData(barcode, productDataState.value.selectedProductName)
        onShowDialogMoreThanOneProductWithBarcode(false, emptyList())
    }
}
