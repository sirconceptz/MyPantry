package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.ProductDao
import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.di.local.dataSource.ProductLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDataSource {
    override fun observeAll(): Flow<List<ProductEntity>> {
        return productDao.observeAll()
    }

    override fun getAll(): List<ProductEntity> {
        return productDao.getAll()
    }

    override fun observeById(id: Int): Flow<ProductEntity> {
        return productDao.observeById(id)
    }

    override suspend fun insert(products: List<ProductEntity>): List<Long> {
        return productDao.insert(products)
    }

    override suspend fun update(products: List<ProductEntity>) {
        productDao.update(products)
    }

    override suspend fun delete(productIds: List<Int>) {
        productIds.forEach { id ->
            productDao.delete(id)
        }
    }

    override suspend fun deleteAll() {
        productDao.deleteAll()
    }
}
