package com.hermanowicz.mypantry.navigation.features.storageLocations.ui

import androidx.lifecycle.ViewModel
import com.hermanowicz.mypantry.navigation.features.storageLocations.state.StorageLocationsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StorageLocationsViewModel @Inject constructor() : ViewModel() {

    private val _storageLocationState = MutableStateFlow(StorageLocationsState())
    var storageLocationState: StateFlow<StorageLocationsState> = _storageLocationState.asStateFlow()

    fun onClickAddNewStorageLocation(isActive: Boolean) {
        _storageLocationState.update { it.copy(showDialogAddNewStorageLocation = isActive) }
    }
}
