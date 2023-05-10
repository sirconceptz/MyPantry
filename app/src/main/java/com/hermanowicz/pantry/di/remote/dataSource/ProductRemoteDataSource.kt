package com.hermanowicz.pantry.di.remote.dataSource

import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.data.remote.dataSource.ProductRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {
    fun observeAll(): Flow<List<ProductEntity>>
    fun observeById(id: Int): Flow<ProductEntity?>
    suspend fun insert(products: List<ProductEntity>): List<Long>
    suspend fun update(products: List<ProductEntity>)
    suspend fun delete(productIds: List<Int>)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRemoteDataSourceModule {

    @Binds
    abstract fun bindMProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource
}
