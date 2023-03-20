package com.hermanowicz.mypantry.data.local.dataSource

import com.hermanowicz.mypantry.data.local.db.ProductDao
import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.local.dataSource.ProductLocalDataSource
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

    override fun observeById(id: Int): Flow<ProductEntity> {
        return productDao.observeById(id)
    }

    override suspend fun insert(products: List<ProductEntity>) {
        productDao.insert(products)
    }

    override suspend fun update(products: List<ProductEntity>) {
        productDao.update(products)
    }

    override suspend fun delete(products: List<ProductEntity>) {
        productDao.delete(products)
    }

    override suspend fun deleteAll() {
        productDao.deleteAll()
    }
}
