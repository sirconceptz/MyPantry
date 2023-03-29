package com.hermanowicz.mypantry.navigation.features.storageLocations.state

sealed class StorageLocationsUiState {
    object Empty : StorageLocationsUiState()
    object Loading : StorageLocationsUiState()
    class Loaded(val data: StorageLocationsModel) : StorageLocationsUiState()
    class Error(val message: String) : StorageLocationsUiState()
}