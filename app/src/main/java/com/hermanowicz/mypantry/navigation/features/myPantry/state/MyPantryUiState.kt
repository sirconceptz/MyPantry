package com.hermanowicz.mypantry.navigation.features.myPantry.state

sealed class MyPantryUiState {
    object Empty : MyPantryUiState()
    object Loading : MyPantryUiState()
    class Loaded(val data: MyPantryModel) : MyPantryUiState()
    class Error(val message: String) : MyPantryUiState()
}
