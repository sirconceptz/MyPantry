package com.hermanowicz.pantry.di.local.dataSource

import com.hermanowicz.pantry.data.local.dataSource.CategoryLocalDataSourceImpl
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    fun observeAll(): Flow<List<CategoryEntity>>
    fun getAll(): List<CategoryEntity>
    fun observeById(id: Int): Flow<CategoryEntity>
    suspend fun insert(category: CategoryEntity)
    suspend fun update(category: CategoryEntity)
    suspend fun delete(category: CategoryEntity)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryLocalDataSourceModule {

    @Binds
    abstract fun bindCategoryLocalDataSource(
        categoryLocalDataSourceImpl: CategoryLocalDataSourceImpl
    ): CategoryLocalDataSource
}
