package com.hermanowicz.pantry.navigation.features.productDetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.domain.GetGroupProductUseCase
import com.hermanowicz.pantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val getGroupProductUseCase: GetGroupProductUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Empty)
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    private val stringId: String = savedStateHandle["id"] ?: "0"
    val productId = stringId.toInt()

    init {
        fetchProducts(productId)
    }

    private fun fetchProducts(productId: Int) {
        _uiState.value = ProductDetailsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                observeAllProductsUseCase().collect { products ->
                    _uiState.value = ProductDetailsUiState.Loaded(
                        ProductDetailsModel(
                            groupProduct = getGroupProductUseCase(productId, products),
                            loadingVisible = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = ProductDetailsUiState.Error(e.toString())
            }
        }
    }
}
