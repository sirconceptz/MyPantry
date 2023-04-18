package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.CategoryDao
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.di.local.dataSource.CategoryLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryLocalDataSourceImpl @Inject constructor(
    private val categoriesDao: CategoryDao
) : CategoryLocalDataSource {
    override fun observeAll(): Flow<List<CategoryEntity>> {
        return categoriesDao.observeAll()
    }

    override fun getAll(): List<CategoryEntity> {
        return categoriesDao.getAll()
    }

    override fun observeById(id: Int): Flow<CategoryEntity> {
        return categoriesDao.observeById(id)
    }

    override suspend fun insert(category: CategoryEntity) {
        categoriesDao.insert(category)
    }

    override suspend fun update(category: CategoryEntity) {
        categoriesDao.update(category)
    }

    override suspend fun delete(category: CategoryEntity) {
        categoriesDao.delete(category)
    }

    override suspend fun deleteAll() {
        categoriesDao.deleteAll()
    }
}
