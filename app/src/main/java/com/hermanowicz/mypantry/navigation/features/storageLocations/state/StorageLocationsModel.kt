package com.hermanowicz.mypantry.navigation.features.storageLocations.state

import com.hermanowicz.mypantry.data.model.StorageLocation

data class StorageLocationsModel(
    var storageLocations: List<StorageLocation> = emptyList(),
    var loadingVisible: Boolean = true
)
