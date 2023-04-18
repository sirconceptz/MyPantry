package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.local.dataSource.StorageLocationLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.StorageLocationRemoteDataSource
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationRepositoryImpl @Inject constructor(
    private val localDataSource: StorageLocationLocalDataSource,
    private val remoteDataSource: StorageLocationRemoteDataSource,
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

    override fun getAllLocal(): List<StorageLocation> {
        return localDataSource.getAll().map { it.toDomainModel() }
    }

    override suspend fun insert(storageLocation: StorageLocation) {
        localDataSource.insert(storageLocation.toEntityModel())
    }

    override suspend fun insertRemote(storageLocations: List<StorageLocation>) {
        remoteDataSource.insert(storageLocations.map { it.toEntityModel() })
    }

    override suspend fun update(storageLocation: StorageLocation) {
        localDataSource.update(storageLocation.toEntityModel())
    }

    override suspend fun delete(storageLocation: StorageLocation) {
        localDataSource.delete(storageLocation.toEntityModel())
    }

    override suspend fun deleteAllCurrentDatabase() {
        localDataSource.deleteAll()
    }

    override suspend fun deleteAllRemote() {
        remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllLocal() {
        localDataSource.deleteAll()
    }
}
