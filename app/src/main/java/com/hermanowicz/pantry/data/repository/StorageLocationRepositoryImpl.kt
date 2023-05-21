package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.local.dataSource.StorageLocationLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.StorageLocationRemoteDataSource
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationRepositoryImpl @Inject constructor(
    private val localDataSource: StorageLocationLocalDataSource,
    private val remoteDataSource: StorageLocationRemoteDataSource,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase
) : StorageLocationRepository {
    override fun observeById(id: Int, databaseMode: DatabaseMode): Flow<StorageLocation> {
        return localDataSource.observeById(id).filterNotNull().map { storageLocationEntity ->
            storageLocationEntity.toDomainModel()
        }.distinctUntilChanged()
    }

    override fun observeAll(databaseMode: DatabaseMode): Flow<List<StorageLocation>> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeAll().map { storageLocationEntities ->
                storageLocationEntities.map { storageLocationEntity -> storageLocationEntity.toDomainModel() }
            }.distinctUntilChanged()
        } else {
            remoteDataSource.observeAll().map { storageLocationEntities ->
                storageLocationEntities.map { storageLocationEntity -> storageLocationEntity.toDomainModel() }
            }.distinctUntilChanged()
        }
    }

    override suspend fun getLastId(databaseMode: DatabaseMode): Int {
        return observeAll(databaseMode).map { storageLocations ->
            storageLocations.maxOf { it.id }
        }.first()
    }

    override suspend fun insert(storageLocation: StorageLocation) {
        if (observeDatabaseModeUseCase().first() == DatabaseMode.LOCAL)
            localDataSource.insert(storageLocation.toEntityModel())
        else {
            val id = getLastId(DatabaseMode.ONLINE) + 1
            remoteDataSource.insert(storageLocation.copy(id = id).toEntityModel())
        }
    }

    override suspend fun insertRemote(storageLocation: StorageLocation) {
        remoteDataSource.insert(storageLocation.toEntityModel())
    }

    override suspend fun update(storageLocation: StorageLocation) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.update(storageLocation.toEntityModel())
        else
            remoteDataSource.update(storageLocation.toEntityModel())
    }

    override suspend fun delete(storageLocation: StorageLocation) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.delete(storageLocation.toEntityModel())
        else
            remoteDataSource.delete(storageLocation.toEntityModel())
    }

    override suspend fun deleteAllCurrentDatabase() {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.deleteAll()
        else
            remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllRemote() {
        remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllLocal() {
        localDataSource.deleteAll()
    }
}
