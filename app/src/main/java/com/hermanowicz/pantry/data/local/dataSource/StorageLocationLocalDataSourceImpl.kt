package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.StorageLocationDao
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.di.local.dataSource.StorageLocationLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationLocalDataSourceImpl @Inject constructor(
    private val storageLocationDao: StorageLocationDao
) : StorageLocationLocalDataSource {
    override fun observeAll(): Flow<List<StorageLocationEntity>> {
        return storageLocationDao.observeAll()
    }

    override fun getAll(): List<StorageLocationEntity> {
        return storageLocationDao.getAll()
    }

    override fun observeById(id: Int): Flow<StorageLocationEntity> {
        return storageLocationDao.observeById(id)
    }

    override suspend fun insert(storageLocation: StorageLocationEntity) {
        storageLocationDao.insert(storageLocation)
    }

    override suspend fun update(storageLocation: StorageLocationEntity) {
        storageLocationDao.update(storageLocation)
    }

    override suspend fun delete(storageLocation: StorageLocationEntity) {
        storageLocationDao.delete(storageLocation)
    }

    override suspend fun deleteAll() {
        storageLocationDao.deleteAll()
    }
}
