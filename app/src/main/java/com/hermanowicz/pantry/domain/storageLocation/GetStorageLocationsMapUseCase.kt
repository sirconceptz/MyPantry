package com.hermanowicz.pantry.domain.storageLocation

import android.content.Context
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.utils.enums.storageLocations.StorageLocations
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetStorageLocationsMapUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : (List<StorageLocation>) -> Map<String, String> {
    override fun invoke(storageLocations: List<StorageLocation>): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map[StorageLocations.CHOOSE.name] = context.getString(StorageLocations.CHOOSE.nameResId)

        for (storageLocation in storageLocations) {
            map[storageLocation.name] = storageLocation.name
        }
        return map
    }
}
