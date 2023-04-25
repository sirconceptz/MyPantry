package com.hermanowicz.pantry.di.remote.dataSource

import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.data.remote.dataSource.StorageLocationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface StorageLocationRemoteDataSource {
    fun observeAll(): Flow<List<StorageLocationEntity>>
    fun observeById(id: Int): Flow<StorageLocationEntity?>
    suspend fun insert(storageLocation: StorageLocationEntity)
    suspend fun update(storageLocation: StorageLocationEntity)
    suspend fun delete(storageLocation: StorageLocationEntity)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageLocationRemoteDataSourceModule {

    @Binds
    abstract fun bindMProductRemoteDataSource(
        storageLocationRemoteDataSourceImpl: StorageLocationRemoteDataSourceImpl
    ): StorageLocationRemoteDataSource
}
