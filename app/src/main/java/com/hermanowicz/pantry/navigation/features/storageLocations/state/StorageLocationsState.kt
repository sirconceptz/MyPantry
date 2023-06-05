package com.hermanowicz.pantry.navigation.features.storageLocations.state

import com.hermanowicz.pantry.data.model.StorageLocation

data class StorageLocationsState(
    var name: String = "",
    var description: String = "",
    var showDialogAddNewStorageLocation: Boolean = false,
    var showDialogEditStorageLocation: Boolean = false,
    var showErrorWrongName: Boolean = false,
    var editedStorageLocation: StorageLocation = StorageLocation(),
    var isEditMode: Boolean = false
)
