package com.hermanowicz.pantry.navigation.features.ownCategories.state

sealed class CategoriesUiState {
    object Empty : CategoriesUiState()
    object Loading : CategoriesUiState()
    class Loaded(val data: CategoriesModel) : CategoriesUiState()
    class Error(val message: String) : CategoriesUiState()
}
