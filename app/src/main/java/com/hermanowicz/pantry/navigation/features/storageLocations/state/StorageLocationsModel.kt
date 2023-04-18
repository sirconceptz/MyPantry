package com.hermanowicz.pantry.navigation.features.storageLocations.state

import com.hermanowicz.pantry.data.model.StorageLocation

data class StorageLocationsModel(
    var storageLocations: List<StorageLocation> = emptyList(),
    var loadingVisible: Boolean = true
)
