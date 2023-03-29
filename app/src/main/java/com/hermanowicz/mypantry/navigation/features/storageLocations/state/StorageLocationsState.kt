package com.hermanowicz.mypantry.navigation.features.storageLocations.state

data class StorageLocationsState(
    var name: String = "",
    var description: String = "",
    var showDialogAddNewStorageLocation: Boolean = false
)
