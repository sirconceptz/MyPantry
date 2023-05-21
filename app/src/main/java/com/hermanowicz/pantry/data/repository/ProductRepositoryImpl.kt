package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.local.dataSource.ProductLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.ProductRemoteDataSource
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase
) : ProductRepository {
    override fun observeById(id: Int, databaseMode: DatabaseMode): Flow<Product> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeById(id).filterNotNull().map { productEntity ->
                productEntity.toDomainModel()
            }.distinctUntilChanged()
        } else {
            remoteDataSource.observeById(id).filterNotNull().map { productEntity ->
                productEntity.toDomainModel()
            }.distinctUntilChanged()
        }
    }

    override fun observeAll(databaseMode: DatabaseMode): Flow<List<Product>> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeAll().map { productEntities ->
                productEntities.map { productEntity -> productEntity.toDomainModel() }
            }.distinctUntilChanged()
        } else {
            remoteDataSource.observeAll().map { productEntities ->
                productEntities.map { productEntity -> productEntity.toDomainModel() }
            }.distinctUntilChanged()
        }
    }

    override suspend fun getLastId(databaseMode: DatabaseMode): Int {
        return try {
            observeAll(databaseMode).map { products ->
                products.maxOf { it.id }
            }.first()
        } catch (exception: Exception) {
            -1
        }
    }

    override suspend fun insert(products: List<Product>): List<Long> {
        val databaseMode = observeDatabaseModeUseCase().first()
        var id = getLastId(DatabaseMode.ONLINE)
        return if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.insert(products.map { product -> product.toEntityModel() })
        else {
            remoteDataSource.insert(products.map { product ->
                id++
                product.copy(id = id).toEntityModel()
            })
        }
    }

    override suspend fun insertRemote(products: List<Product>) {
        remoteDataSource.insert(products.map { product -> product.toEntityModel() })
    }

    override suspend fun update(products: List<Product>) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.update(products.map { product -> product.toEntityModel() })
        else
            remoteDataSource.update(products.map { product -> product.toEntityModel() })
    }

    override suspend fun delete(productIds: List<Int>) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.delete(productIds)
        else
            remoteDataSource.delete(productIds)
    }

    override suspend fun deleteAllCurrentDatabase() {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.deleteAll()
        else
            remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllRemote() {
        remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllLocal() {
        localDataSource.deleteAll()
    }
}
