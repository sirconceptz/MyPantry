package com.hermanowicz.pantry.domain.storageLocation

import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import javax.inject.Inject

class UpdateStorageLocationUseCase @Inject constructor(
    private val storageLocationRepository: StorageLocationRepository
) : suspend (StorageLocation) -> Unit {
    override suspend fun invoke(storageLocation: StorageLocation) {
        storageLocationRepository.update(storageLocation)
    }
}
