package com.hermanowicz.mypantry.navigation.features.editProduct.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.domain.ObserveProductByIdUseCase
import com.hermanowicz.mypantry.domain.UpdateProductsUseCase
import com.hermanowicz.mypantry.navigation.features.newProduct.state.NewProductUiState
import com.hermanowicz.mypantry.utils.ProductDataState
import com.hermanowicz.mypantry.utils.RegexFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val observeProductByIdUseCase: ObserveProductByIdUseCase,
    private val updateProductsUseCase: UpdateProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewProductUiState.Empty)
    var uiState: StateFlow<NewProductUiState> = _uiState.asStateFlow()

    private val _productDataState = MutableStateFlow(ProductDataState())
    var productDataState: StateFlow<ProductDataState> = _productDataState.asStateFlow()

    private fun fetchProductData(productId: Int) {
        viewModelScope.launch {
            observeProductByIdUseCase(productId).map { product ->
                _productDataState.value = ProductDataState(
                    name = product.name,
                    expirationDate = product.expirationDate,
                    productionDate = product.productionDate,
                    composition = product.composition,
                    healingProperties = product.healingProperties,
                    dosage = product.dosage,
                    hasSugar = product.hasSugar,
                    hasSalt = product.hasSalt,
                    isVege = product.isVege,
                    isBio = product.isBio,
                    weight = product.weight.toString(),
                    volume = product.volume.toString()
                )
            }.collect()
        }
    }

    fun onSaveClick() {
        val product = Product(
            name = productDataState.value.name,
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
            volume = productDataState.value.volume.toIntOrNull() ?: 0
        )
        viewModelScope.launch(Dispatchers.IO) {
            val products: MutableList<Product> = mutableListOf()
            val quantity = productDataState.value.quantity.toInt()
            for (i in 0..quantity) {
                products.add(product)
            }
            updateProductsUseCase(products)
        }
    }

    fun onNameChange(name: String) {
        _productDataState.update { it.copy(name = name) }
    }

    fun onExpirationDateChange(expirationDate: String) {
        _productDataState.update { it.copy(expirationDate = expirationDate) }
    }

    fun onProductionDateChange(productionDate: String) {
        _productDataState.update { it.copy(productionDate = productionDate) }
    }

    fun onQuantityChange(quantity: String) {
        if (quantity.matches(RegexFormats.NUMBER.regex))
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
        if (weight.matches(RegexFormats.NUMBER.regex))
            _productDataState.update { it.copy(weight = weight) }
    }

    fun onVolumeChange(volume: String) {
        if (volume.matches(RegexFormats.NUMBER.regex))
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

    fun onMainCategoryChange(mainCategory: String) {
        _productDataState.update {
            it.copy(
                mainCategory = mainCategory,
                showMainCategoryDropdown = false
            )
        }
    }
}
