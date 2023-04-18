package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.repository.CategoriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun observeById(id: Int): Flow<Category>
    fun observeAll(): Flow<List<Category>>
    fun getMainCategories(): Map<String, String>
    fun getOwnCategories(): List<Category>
    suspend fun insert(category: Category)
    suspend fun update(category: Category)
    suspend fun delete(category: Category)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository
}
