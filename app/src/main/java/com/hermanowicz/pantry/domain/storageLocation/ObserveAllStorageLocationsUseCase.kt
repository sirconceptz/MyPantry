package com.hermanowicz.pantry.domain.storageLocation

import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllStorageLocationsUseCase @Inject constructor(
    private val storageLocationRepository: StorageLocationRepository
) : (DatabaseMode) -> Flow<List<StorageLocation>> {
    override fun invoke(databaseMode: DatabaseMode): Flow<List<StorageLocation>> {
        return storageLocationRepository.observeAll(databaseMode).distinctUntilChanged()
    }
}
