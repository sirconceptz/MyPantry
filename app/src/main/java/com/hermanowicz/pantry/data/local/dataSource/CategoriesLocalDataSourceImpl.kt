package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.CategoriesDao
import com.hermanowicz.pantry.data.local.model.CategoriesEntity
import com.hermanowicz.pantry.di.local.dataSource.CategoriesLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesLocalDataSourceImpl @Inject constructor(
    private val categoriesDao: CategoriesDao
) : CategoriesLocalDataSource {
    override fun observeAll(): Flow<List<CategoriesEntity>> {
        return categoriesDao.observeAll()
    }

    override fun getAll(): List<CategoriesEntity> {
        return categoriesDao.getAll()
    }

    override fun observeById(id: Int): Flow<CategoriesEntity> {
        return categoriesDao.observeById(id)
    }

    override suspend fun insert(category: CategoriesEntity) {
        categoriesDao.insert(category)
    }

    override suspend fun update(category: CategoriesEntity) {
        categoriesDao.update(category)
    }

    override suspend fun delete(category: CategoriesEntity) {
        categoriesDao.delete(category)
    }

    override suspend fun deleteAll() {
        categoriesDao.deleteAll()
    }
}
