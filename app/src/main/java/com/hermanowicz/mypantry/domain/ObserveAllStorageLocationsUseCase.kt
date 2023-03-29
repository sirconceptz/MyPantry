package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.di.repository.StorageLocationRepository
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
