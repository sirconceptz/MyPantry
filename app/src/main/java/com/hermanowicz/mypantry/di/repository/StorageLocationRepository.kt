package com.hermanowicz.mypantry.di.repository

import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.data.repository.StorageLocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface StorageLocationRepository {
    fun observeById(id: Int): Flow<StorageLocation>
    fun observeAll(): Flow<List<StorageLocation>>
    suspend fun insert(storageLocation: StorageLocation)
    suspend fun update(storageLocation: StorageLocation)
    suspend fun delete(storageLocation: StorageLocation)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageLocationRepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        storageLocationRepositoryImpl: StorageLocationRepositoryImpl
    ): StorageLocationRepository
}
