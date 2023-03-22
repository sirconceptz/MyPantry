package com.hermanowicz.mypantry.di.repository

import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeById(id: Int): Flow<Product>
    fun observeAll(): Flow<List<Product>>
    suspend fun insert(products: List<Product>)
    suspend fun update(products: List<Product>)
    suspend fun delete(products: List<Product>)
    suspend fun deleteAll()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
