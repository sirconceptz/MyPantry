package com.hermanowicz.mypantry.di.local.dataSource

import com.hermanowicz.mypantry.data.local.dataSource.CategoriesLocalDataSourceImpl
import com.hermanowicz.mypantry.data.local.model.CategoriesEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface CategoriesLocalDataSource {
    fun observeAll(): Flow<List<CategoriesEntity>>
    fun observeById(id: Int): Flow<CategoriesEntity>
    suspend fun insert(category: CategoriesEntity)
    suspend fun update(category: CategoriesEntity)
    suspend fun delete(category: CategoriesEntity)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesLocalDataSourceModule {

    @Binds
    abstract fun bindCategoriesLocalDataSource(
        categoriesLocalDataSourceImpl: CategoriesLocalDataSourceImpl
    ): CategoriesLocalDataSource
}
