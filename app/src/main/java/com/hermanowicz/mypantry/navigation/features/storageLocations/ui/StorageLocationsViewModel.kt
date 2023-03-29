package com.hermanowicz.mypantry.navigation.features.storageLocations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.domain.DeleteStorageLocationUseCase
import com.hermanowicz.mypantry.domain.ObserveAllStorageLocationsUseCase
import com.hermanowicz.mypantry.domain.SaveStorageLocationsUseCase
import com.hermanowicz.mypantry.navigation.features.storageLocations.state.StorageLocationsModel
import com.hermanowicz.mypantry.navigation.features.storageLocations.state.StorageLocationsState
import com.hermanowicz.mypantry.navigation.features.storageLocations.state.StorageLocationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageLocationsViewModel @Inject constructor(
    private val observeAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase,
    private val saveStorageLocationsUseCase: SaveStorageLocationsUseCase,
    private val deleteStorageLocationUseCase: DeleteStorageLocationUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<StorageLocationsUiState>(StorageLocationsUiState.Empty)
    val uiState: StateFlow<StorageLocationsUiState> = _uiState

    private val _storageLocationsState = MutableStateFlow(StorageLocationsState())
    var storageLocationState: StateFlow<StorageLocationsState> =
        _storageLocationsState.asStateFlow()

    init {
        fetchStorageLocations()
    }

    fun fetchStorageLocations() {
        _uiState.value = StorageLocationsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                observeAllStorageLocationsUseCase().collect { storageLocationList ->
                    _uiState.value = StorageLocationsUiState.Loaded(
                        StorageLocationsModel(
                            storageLocations = storageLocationList,
                            loadingVisible = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = StorageLocationsUiState.Error(e.toString())
            }
        }
    }

    fun onNameChange(name: String) {
        _storageLocationsState.update { it.copy(name = name) }
    }

    fun onDescriptionChange(description: String) {
        _storageLocationsState.update { it.copy(description = description) }
    }

    fun onClickSaveStorageLocation() {
        val storageLocation = StorageLocation(
            name = storageLocationState.value.name,
            description = storageLocationState.value.description
        )
        viewModelScope.launch(Dispatchers.IO) {
            saveStorageLocationsUseCase(storageLocation)
        }
        _storageLocationsState.update { it.copy(showDialogAddNewStorageLocation = false) }
    }

    fun onShowDialogAddNewStorageLocation(isActive: Boolean) {
        _storageLocationsState.update { it.copy(showDialogAddNewStorageLocation = isActive) }
    }

    fun onEditMode(isEditMode: Boolean) {
        _storageLocationsState.update { it.copy(isEditMode = isEditMode) }
    }

    fun onDeleteStorageLocation(storageLocation: StorageLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStorageLocationUseCase(storageLocation)
        }
    }
}
