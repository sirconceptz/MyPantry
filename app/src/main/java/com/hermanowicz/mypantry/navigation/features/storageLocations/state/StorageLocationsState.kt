package com.hermanowicz.mypantry.navigation.features.storageLocations.state

import com.hermanowicz.mypantry.data.model.StorageLocation

data class StorageLocationsState(
    var name: String = "",
    var description: String = "",
    var showDialogAddNewStorageLocation: Boolean = false,
    var showDialogEditStorageLocation: Boolean = false,
    var editedStorageLocation: StorageLocation = StorageLocation(),
    var isEditMode: Boolean = false
)
