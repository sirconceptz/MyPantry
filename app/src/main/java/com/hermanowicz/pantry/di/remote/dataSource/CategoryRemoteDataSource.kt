package com.hermanowicz.pantry.di.remote.dataSource

import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.data.remote.dataSource.CategoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface CategoryRemoteDataSource {
    suspend fun observeAll(): Flow<List<CategoryEntity>>
    fun observeById(id: Int): Flow<CategoryEntity>
    suspend fun insert(categories: List<CategoryEntity>)
    suspend fun update(categories: List<CategoryEntity>)
    suspend fun delete(categories: List<CategoryEntity>)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRemoteDataSourceModule {

    @Binds
    abstract fun bindCategoryRemoteDataSource(
        categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl
    ): CategoryRemoteDataSource
}
