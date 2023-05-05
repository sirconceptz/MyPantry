package com.hermanowicz.pantry.navigation.features.productDetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.domain.CheckIsProductsHashcodeCorrectUseCase
import com.hermanowicz.pantry.domain.FetchDatabaseModeUseCase
import com.hermanowicz.pantry.domain.GetGroupProductUseCase
import com.hermanowicz.pantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val getGroupProductUseCase: GetGroupProductUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val fetchDatabaseModeUseCase: FetchDatabaseModeUseCase,
    private val checkIsProductsHashcodeCorrectUseCase: CheckIsProductsHashcodeCorrectUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Empty)
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    private val idAndHashcode: String = savedStateHandle["idAndHashcode"] ?: ";"
    private val argumentsArray = idAndHashcode.split(";")
    val productId = argumentsArray[0].toInt()
    val productHashcode = argumentsArray[1]

    init {
        fetchProducts(productId)
    }

    private fun fetchProducts(productId: Int) {
        _uiState.value = ProductDetailsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fetchDatabaseModeUseCase().collect { databaseMode ->
                    observeAllProductsUseCase(databaseMode).collect { products ->
                        if (checkIsProductsHashcodeCorrectUseCase(
                                productId,
                                productHashcode,
                                products
                            )
                        ) {
                            _uiState.value = ProductDetailsUiState.Loaded(
                                ProductDetailsModel(
                                    groupProduct = getGroupProductUseCase(productId, products),
                                    loadingVisible = false
                                )
                            )
                        } else {
                            _uiState.value = ProductDetailsUiState.Error("No correct product")
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ProductDetailsUiState.Error(e.toString())
            }
        }
    }
}