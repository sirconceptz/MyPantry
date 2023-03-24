package com.hermanowicz.mypantry.navigation.features.productDetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.domain.GetGroupProductUseCase
import com.hermanowicz.mypantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.mypantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.mypantry.navigation.features.productDetails.state.ProductDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val getGroupProductUseCase: GetGroupProductUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Empty)
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    init {
        val productId: String = savedStateHandle["id"] ?: "0"
        fetchProducts(productId.toInt())
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
