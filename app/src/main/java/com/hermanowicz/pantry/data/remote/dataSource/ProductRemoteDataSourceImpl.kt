package com.hermanowicz.pantry.data.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.di.remote.dataSource.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSourceImpl @Inject constructor() : ProductRemoteDataSource {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("products")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override suspend fun observeAll(): Flow<List<ProductEntity>> {
        if (userId.isNotEmpty()) {
            return try {
                val products = mutableListOf<ProductEntity>()
                val children = databaseReference
                    .child(userId).get().await().children
                children.forEach {
                    val product = it.getValue(ProductEntity::class.java)!!
                    products.add(product)
                }
                flowOf(products)
            } catch (exception: Exception) {
                flowOf(emptyList())
            }
        }
        return flowOf(emptyList())
    }

    override fun observeById(id: Int): Flow<ProductEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(products: List<ProductEntity>) {
        if (userId.isNotEmpty()) {
            products.forEach { product ->
                databaseReference.child(userId).child(product.id.toString()).setValue(product)
            }
        }
    }

    override suspend fun update(products: List<ProductEntity>) {
        if (userId.isNotEmpty()) {
            products.forEach { product ->
                databaseReference.child(userId).child(product.id.toString()).setValue(product)
            }
        }
    }

    override suspend fun delete(products: List<ProductEntity>) {
        if (userId.isNotEmpty()) {
            products.forEach { product ->
                databaseReference.child(userId).child(product.id.toString()).removeValue()
            }
        }
    }

    override suspend fun deleteAll() {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).removeValue()
        }
    }
}
