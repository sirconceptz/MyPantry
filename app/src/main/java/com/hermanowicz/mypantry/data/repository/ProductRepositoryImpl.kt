package com.hermanowicz.mypantry.data.repository

import com.hermanowicz.mypantry.data.mapper.toDomainModel
import com.hermanowicz.mypantry.data.mapper.toEntityModel
import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.di.local.dataSource.ProductLocalDataSource
import com.hermanowicz.mypantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun observeById(id: Int): Flow<Product> {
        return localDataSource.observeById(id).filterNotNull().map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    override fun observeAll(): Flow<List<Product>> {
        return localDataSource.observeAll().map { productEntities ->
            productEntities.map { productEntity -> productEntity.toDomainModel() }
        }
    }

    override suspend fun insert(products: List<Product>) {
        localDataSource.insert(products.map { product -> product.toEntityModel() })
    }

    override suspend fun update(products: List<Product>) {
        localDataSource.update(products.map { product -> product.toEntityModel() })
    }

    override suspend fun delete(products: List<Product>) {
        localDataSource.delete(products.map { product -> product.toEntityModel() })
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}
