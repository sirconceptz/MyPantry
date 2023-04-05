package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.di.repository.StorageLocationRepository
import javax.inject.Inject

class UpdateStorageLocationUseCase @Inject constructor(
    private val storageLocationRepository: StorageLocationRepository
) : suspend (StorageLocation) -> Unit {
    override suspend fun invoke(storageLocation: StorageLocation) {
        storageLocationRepository.update(storageLocation)
    }
}
