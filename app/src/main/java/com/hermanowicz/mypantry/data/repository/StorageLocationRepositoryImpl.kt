package com.hermanowicz.mypantry.data.repository

import com.hermanowicz.mypantry.data.mapper.toDomainModel
import com.hermanowicz.mypantry.data.mapper.toEntityModel
import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.di.local.dataSource.StorageLocationLocalDataSource
import com.hermanowicz.mypantry.di.repository.StorageLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationRepositoryImpl @Inject constructor(
    private val localDataSource: StorageLocationLocalDataSource
) : StorageLocationRepository {
    override fun observeById(id: Int): Flow<StorageLocation> {
        return localDataSource.observeById(id).filterNotNull().map { storageLocationEntity ->
            storageLocationEntity.toDomainModel()
        }
    }

    override fun observeAll(): Flow<List<StorageLocation>> {
        return localDataSource.observeAll().map { storageLocationEntities ->
            storageLocationEntities.map { storageLocationEntity -> storageLocationEntity.toDomainModel() }
        }
    }

    override suspend fun insert(storageLocation: StorageLocation) {
        localDataSource.insert(storageLocation.toEntityModel())
    }

    override suspend fun update(storageLocation: StorageLocation) {
        localDataSource.update(storageLocation.toEntityModel())
    }

    override suspend fun delete(storageLocation: StorageLocation) {
        localDataSource.delete(storageLocation.toEntityModel())
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}
