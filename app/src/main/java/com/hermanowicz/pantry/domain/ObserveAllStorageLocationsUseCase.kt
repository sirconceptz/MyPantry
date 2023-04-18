package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllStorageLocationsUseCase @Inject constructor(
    private val storageLocationRepository: StorageLocationRepository
) : () -> Flow<List<StorageLocation>> {
    override fun invoke(): Flow<List<StorageLocation>> {
        return storageLocationRepository.observeAll().distinctUntilChanged()
    }
}
