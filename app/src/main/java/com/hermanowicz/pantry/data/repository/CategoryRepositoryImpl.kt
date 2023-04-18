package com.hermanowicz.pantry.data.repository

import android.content.Context
import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.local.dataSource.CategoryLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.CategoryRemoteDataSource
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.utils.category.MainCategoriesTypes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
    @ApplicationContext private val context: Context
) : CategoryRepository {
    override fun observeById(id: Int): Flow<Category> {
        return localDataSource.observeById(id).filterNotNull().map { storageLocationEntity ->
            storageLocationEntity.toDomainModel()
        }
    }

    override fun observeAll(): Flow<List<Category>> {
        return localDataSource.observeAll().map { categoriesEntities ->
            categoriesEntities.map { categoryEntity -> categoryEntity.toDomainModel() }
        }
    }

    override fun getAllLocal(): List<Category> {
        return localDataSource.getAll().map { it.toDomainModel() }
    }

    override fun getMainCategories(): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        enumValues<MainCategoriesTypes>().forEach { category ->
            map[category.name] = context.getString(category.nameResId)
        }
        return map
    }

    override fun getOwnCategories(): List<Category> {
        return localDataSource.getAll().map { categoriesEntity -> categoriesEntity.toDomainModel() }
    }

    override suspend fun insert(category: Category) {
        localDataSource.insert(category.toEntityModel())
    }

    override suspend fun insertRemote(categories: List<Category>) {
        remoteDataSource.insert(categories.map { it.toEntityModel() })
    }

    override suspend fun update(category: Category) {
        localDataSource.update(category.toEntityModel())
    }

    override suspend fun delete(category: Category) {
        localDataSource.delete(category.toEntityModel())
    }

    override suspend fun deleteAllCurrentDatabase() {
        localDataSource.deleteAll()
    }

    override suspend fun deleteAllRemote() {
        remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllLocal() {
        localDataSource.deleteAll()
    }
}
