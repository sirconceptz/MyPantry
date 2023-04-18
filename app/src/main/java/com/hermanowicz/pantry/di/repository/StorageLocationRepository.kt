package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.data.repository.StorageLocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface StorageLocationRepository {
    fun observeById(id: Int): Flow<StorageLocation>
    fun observeAll(): Flow<List<StorageLocation>>
    fun getAllLocal(): List<StorageLocation>
    suspend fun insert(storageLocation: StorageLocation)
    suspend fun insertRemote(storageLocations: List<StorageLocation>)
    suspend fun update(storageLocation: StorageLocation)
    suspend fun delete(storageLocation: StorageLocation)
    suspend fun deleteAllCurrentDatabase()
    suspend fun deleteAllRemote()
    suspend fun deleteAllLocal()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageLocationRepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        storageLocationRepositoryImpl: StorageLocationRepositoryImpl
    ): StorageLocationRepository
}
