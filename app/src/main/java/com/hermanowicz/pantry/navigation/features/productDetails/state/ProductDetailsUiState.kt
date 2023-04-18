package com.hermanowicz.pantry.navigation.features.productDetails.state

sealed class ProductDetailsUiState {
    object Empty : ProductDetailsUiState()
    object Loading : ProductDetailsUiState()
    class Loaded(val data: ProductDetailsModel) : ProductDetailsUiState()
    class Error(val message: String) : ProductDetailsUiState()
}
