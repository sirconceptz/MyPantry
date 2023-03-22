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

    fun onNameChange(name: String) {
        _productDataState.update { it.copy(name = name) }
    }

    fun onDosageChange(dosage: String) {
        _productDataState.update { it.copy(dosage = dosage) }
    }

    fun onSaveClick() {
        val product = Product(
            name = productDataState.value.name,
            dosage = productDataState.value.dosage,
        )
        viewModelScope.launch(Dispatchers.IO) {
            saveProductsUseCase(listOf(product))
        }
    }
}
