package com.hermanowicz.pantry.di.remote.dataSource

import com.hermanowicz.pantry.data.local.model.CategoryEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface CategoryRemoteDataSource {
    suspend fun observeAll(): Flow<List<CategoryEntity>>
    fun observeById(id: Int): Flow<CategoryEntity>
    suspend fun insert(products: List<CategoryEntity>)
    suspend fun update(products: List<CategoryEntity>)
    suspend fun delete(products: List<CategoryEntity>)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRemoteDataSourceModule {

    @Binds
    abstract fun bindMProductRemoteDataSource(
        categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl
    ): CategoryRemoteDataSource
}
