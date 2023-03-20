package com.hermanowicz.mypantry.data.remote.dataSource

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.remote.dataSource.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSourceImpl : ProductRemoteDataSource {
    override fun observeAll(): Flow<List<ProductEntity>> {
        TODO("Not yet implemented")
    }

    override fun observeById(id: Int): Flow<ProductEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(products: List<ProductEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(products: List<ProductEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(products: List<ProductEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}