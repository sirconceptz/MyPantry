package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.repository.CategoryRepositoryImpl
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeById(id: Int, databaseMode: DatabaseMode): Flow<Category>
    fun observeAll(databaseMode: DatabaseMode): Flow<List<Category>>
    suspend fun getLastId(databaseMode: DatabaseMode): Int
    suspend fun insert(category: Category)
    suspend fun insertRemote(category: Category)
    suspend fun update(category: Category)
    suspend fun delete(category: Category)
    suspend fun deleteAllCurrentDatabase()
    suspend fun deleteAllRemote()
    suspend fun deleteAllLocal()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository
}
