package com.hermanowicz.mypantry.navigation.features.newProduct.state

sealed class NewProductUiState {
    object Empty : NewProductUiState()
    object Loading : NewProductUiState()
    class Loaded(val data: NewProductModel) : NewProductUiState()
    class Error(val message: String) : NewProductUiState()
}
