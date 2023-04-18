package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import javax.inject.Inject

class SaveStorageLocationsUseCase @Inject constructor(
    private val storageLocationRepository: StorageLocationRepository
) : suspend (StorageLocation) -> Unit {
    override suspend fun invoke(storageLocation: StorageLocation) {
        storageLocationRepository.insert(storageLocation)
    }
}
