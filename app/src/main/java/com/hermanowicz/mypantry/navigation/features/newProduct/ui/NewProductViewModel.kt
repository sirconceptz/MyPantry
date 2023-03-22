package com.hermanowicz.mypantry.navigation.features.newProduct.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.domain.SaveProductsUseCase
import com.hermanowicz.mypantry.navigation.features.newProduct.state.NewProductUiState
import com.hermanowicz.mypantry.utils.ProductDataState
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
    private val saveProductsUseCase: SaveProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewProductUiState.Empty)
    var uiState: StateFlow<NewProductUiState> = _uiState.asStateFlow()

    private val _productDataState = MutableStateFlow(ProductDataState())
    var productDataState: StateFlow<ProductDataState> = _productDataState.asStateFlow()

    val numberPattern = Regex("^\\d+\$")

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
            saveProductsUseCase(products)
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
        if (quantity.matches(numberPattern))
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
        if (weight.matches(numberPattern))
            _productDataState.update { it.copy(weight = weight) }
    }

    fun onVolumeChange(volume: String) {
        if (volume.matches(numberPattern))
            _productDataState.update { it.copy(volume = volume) }
    }
}
