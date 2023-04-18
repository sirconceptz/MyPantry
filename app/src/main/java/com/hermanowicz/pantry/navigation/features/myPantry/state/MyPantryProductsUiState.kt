package com.hermanowicz.pantry.navigation.features.myPantry.state

sealed class MyPantryProductsUiState {
    object Empty : MyPantryProductsUiState()
    object Loading : MyPantryProductsUiState()
    class Loaded(val data: MyPantryModel) : MyPantryProductsUiState()
    class Error(val message: String) : MyPantryProductsUiState()
}
