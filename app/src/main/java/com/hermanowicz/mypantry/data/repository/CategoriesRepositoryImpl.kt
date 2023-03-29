package com.hermanowicz.mypantry.data.repository

import com.hermanowicz.mypantry.data.mapper.toDomainModel
import com.hermanowicz.mypantry.data.mapper.toEntityModel
import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.di.local.dataSource.CategoriesLocalDataSource
import com.hermanowicz.mypantry.di.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepositoryImpl @Inject constructor(
    private val localDataSource: CategoriesLocalDataSource
) : CategoriesRepository {
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

    override suspend fun insert(category: Category) {
        localDataSource.insert(category.toEntityModel())
    }

    override suspend fun update(category: Category) {
        localDataSource.update(category.toEntityModel())
    }

    override suspend fun delete(category: Category) {
        localDataSource.delete(category.toEntityModel())
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}
