package com.hermanowicz.pantry.di.local.dataSource

import com.hermanowicz.pantry.data.local.dataSource.ProductLocalDataSourceImpl
import com.hermanowicz.pantry.data.local.model.ProductEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {
    fun observeAll(): Flow<List<ProductEntity>>
    fun getAll(): List<ProductEntity>
    fun observeById(id: Int): Flow<ProductEntity>
    suspend fun insert(products: List<ProductEntity>): List<Long>
    suspend fun update(products: List<ProductEntity>)
    suspend fun delete(productIds: List<Int>)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductLocalDataSourceModule {

    @Binds
    abstract fun bindMProductLocalDataSource(
        productLocalDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource
}
