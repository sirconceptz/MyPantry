package com.hermanowicz.mypantry.di.remote.dataSource

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.data.remote.dataSource.ProductRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {
    fun observeAll(): Flow<List<ProductEntity>>
    fun observeById(id: Int): Flow<ProductEntity>
    suspend fun insert(products: List<ProductEntity>)
    suspend fun update(products: List<ProductEntity>)
    suspend fun delete(products: List<ProductEntity>)
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
