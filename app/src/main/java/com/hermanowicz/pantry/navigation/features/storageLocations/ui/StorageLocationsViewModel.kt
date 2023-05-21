package com.hermanowicz.pantry.navigation.features.storageLocations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.domain.storageLocation.DeleteStorageLocationUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.storageLocation.ObserveAllStorageLocationsUseCase
import com.hermanowicz.pantry.domain.storageLocation.SaveStorageLocationsUseCase
import com.hermanowicz.pantry.domain.storageLocation.UpdateStorageLocationUseCase
import com.hermanowicz.pantry.navigation.features.storageLocations.state.StorageLocationsModel
import com.hermanowicz.pantry.navigation.features.storageLocations.state.StorageLocationsState
import com.hermanowicz.pantry.navigation.features.storageLocations.state.StorageLocationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageLocationsViewModel @Inject constructor(
    private val observeAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase,
    private val saveStorageLocationsUseCase: SaveStorageLocationsUseCase,
    private val deleteStorageLocationUseCase: DeleteStorageLocationUseCase,
    private val updateStorageLocationUseCase: UpdateStorageLocationUseCase,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<StorageLocationsUiState>(StorageLocationsUiState.Empty)
    val uiState: StateFlow<StorageLocationsUiState> = _uiState

    private val _storageLocationsState = MutableStateFlow(StorageLocationsState())
    var storageLocationState: StateFlow<StorageLocationsState> =
        _storageLocationsState.asStateFlow()

    init {
        observeStorageLocations()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeStorageLocations() {
        _uiState.value = StorageLocationsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                observeDatabaseModeUseCase()
                    .flatMapLatest { databaseMode ->
                        observeAllStorageLocationsUseCase(databaseMode)
                    }.onEach {
                        _uiState.value = StorageLocationsUiState.Loaded(
                            StorageLocationsModel(
                                storageLocations = it,
                                loadingVisible = false
                            )
                        )
                    }
                    .launchIn(viewModelScope)
            } catch (e: Exception) {
                _uiState.value = StorageLocationsUiState.Error(e.toString())
            }
        }
    }

    fun onNameChange(name: String) {
        _storageLocationsState.update { it.copy(name = name) }
    }

    fun onEditStorageLocationNameChange(name: String) {
        _storageLocationsState.update {
            it.copy(
                editedStorageLocation = StorageLocation(
                    id = storageLocationState.value.editedStorageLocation.id,
                    name = name,
                    description = storageLocationState.value.editedStorageLocation.description
                )
            )
        }
    }

    fun onDescriptionChange(description: String) {
        _storageLocationsState.update { it.copy(description = description) }
    }

    fun onEditStorageLocationDescriptionChange(description: String) {
        _storageLocationsState.update {
            it.copy(
                editedStorageLocation = StorageLocation(
                    id = storageLocationState.value.editedStorageLocation.id,
                    name = storageLocationState.value.editedStorageLocation.name,
                    description = description
                )
            )
        }
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

    fun onShowEditStorageLocation(storageLocation: StorageLocation) {
        _storageLocationsState.update {
            it.copy(
                showDialogEditStorageLocation = true,
                editedStorageLocation = storageLocation
            )
        }
    }

    fun onHideDialogEditStorageLocation() {
        _storageLocationsState.update { it.copy(showDialogEditStorageLocation = false) }
    }

    fun onDeleteStorageLocation(storageLocation: StorageLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStorageLocationUseCase(storageLocation)
        }
    }

    fun onSaveEditedStorageLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            updateStorageLocationUseCase(storageLocationState.value.editedStorageLocation)
        }
        onHideDialogEditStorageLocation()
    }
}
