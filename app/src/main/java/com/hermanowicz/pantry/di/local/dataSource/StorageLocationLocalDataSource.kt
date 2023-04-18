package com.hermanowicz.pantry.di.local.dataSource

import com.hermanowicz.pantry.data.local.dataSource.StorageLocationLocalDataSourceImpl
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface StorageLocationLocalDataSource {
    fun observeAll(): Flow<List<StorageLocationEntity>>
    fun observeById(id: Int): Flow<StorageLocationEntity>
    suspend fun insert(storageLocation: StorageLocationEntity)
    suspend fun update(storageLocation: StorageLocationEntity)
    suspend fun delete(storageLocation: StorageLocationEntity)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageLocationLocalDataSourceModule {

    @Binds
    abstract fun bindMProductLocalDataSource(
        storageLocationLocalDataSourceImpl: StorageLocationLocalDataSourceImpl
    ): StorageLocationLocalDataSource
}
