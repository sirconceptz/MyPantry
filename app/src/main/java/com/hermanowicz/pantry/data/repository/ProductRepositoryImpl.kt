package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.local.dataSource.ProductLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.ProductRemoteDataSource
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource,
    private val settingsRepository: SettingsRepository
) : ProductRepository {
    override fun observeById(id: Int): Flow<Product> {
        return localDataSource.observeById(id).filterNotNull().map { productEntity ->
            productEntity.toDomainModel()
        }
    }

    override fun observeAll(databaseMode: DatabaseMode): Flow<List<Product>> {
        lateinit var flow: Flow<List<Product>>
        if (databaseMode == DatabaseMode.LOCAL) {
            flow = localDataSource.observeAll().map { productEntities ->
                productEntities.map { productEntity -> productEntity.toDomainModel() }
            }
        } else remoteDataSource.observeAll {
            flow = it.map { productEntities ->
                productEntities.map { productEntity -> productEntity.toDomainModel() }
            }
        }
        return flow
    }

    override fun getAllLocal(): List<Product> {
        return localDataSource.getAll().map { it.toDomainModel() }
    }

    override suspend fun insert(products: List<Product>) {
        localDataSource.insert(products.map { product -> product.toEntityModel() })
    }

    override suspend fun insertRemote(products: List<Product>) {
        remoteDataSource.insert(products.map { product -> product.toEntityModel() })
    }

    override suspend fun update(products: List<Product>) {
        localDataSource.update(products.map { product -> product.toEntityModel() })
    }

    override suspend fun delete(products: List<Product>) {
        localDataSource.delete(products.map { product -> product.toEntityModel() })
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
