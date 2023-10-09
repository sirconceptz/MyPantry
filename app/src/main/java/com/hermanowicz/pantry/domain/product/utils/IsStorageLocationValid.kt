package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.utils.enums.storageLocations.StorageLocations

class IsStorageLocationValid {
    companion object {
        fun isStorageLocationValid(
            productStorageLocation: String,
            filterProductStorageLocation: String
        ): Boolean {
            return filterProductStorageLocation == StorageLocations.CHOOSE.name || filterProductStorageLocation.isEmpty() || productStorageLocation == filterProductStorageLocation
        }
    }
}
