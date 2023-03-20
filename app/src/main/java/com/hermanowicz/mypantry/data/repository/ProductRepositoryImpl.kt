package com.hermanowicz.mypantry.data.repository

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.local.dataSource.ProductLocalDataSource
import com.hermanowicz.mypantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun observeById(id: Int): Flow<ProductEntity> {
        return localDataSource.observeById(id)
    }

    override fun observeAll(): Flow<List<ProductEntity>> {
        return localDataSource.observeAll()
    }

    override suspend fun insert(products: List<ProductEntity>) {
        localDataSource.insert(products)
    }

    override suspend fun update(products: List<ProductEntity>) {
        localDataSource.update(products)
    }

    override suspend fun delete(products: List<ProductEntity>) {
        localDataSource.delete(products)
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}
