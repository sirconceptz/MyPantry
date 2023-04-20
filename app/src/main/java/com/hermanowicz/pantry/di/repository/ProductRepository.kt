package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.data.repository.ProductRepositoryImpl
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeById(id: Int): Flow<Product>
    fun observeAll(databaseMode: DatabaseMode): Flow<List<Product>>
    fun getAllLocal(): List<Product>
    suspend fun insert(products: List<Product>)
    suspend fun insertRemote(products: List<Product>)
    suspend fun update(products: List<Product>)
    suspend fun delete(products: List<Product>)
    suspend fun deleteAllCurrentDatabase()
    suspend fun deleteAllRemote()
    suspend fun deleteAllLocal()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
