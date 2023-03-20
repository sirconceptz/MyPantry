package com.hermanowicz.mypantry.di.repository

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeById(id: Int): Flow<ProductEntity>
    fun observeAll(): Flow<List<ProductEntity>>
    suspend fun insert(products: List<ProductEntity>)
    suspend fun update(products: List<ProductEntity>)
    suspend fun delete(products: List<ProductEntity>)
    suspend fun deleteAll()
}
