package com.hermanowicz.pantry.data.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.di.remote.dataSource.ProductRemoteDataSource
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSourceImpl @Inject constructor(
) : ProductRemoteDataSource {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("products")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun observeAll(): Flow<List<ProductEntity>> {
        return callbackFlow {
            if (userId.isNotEmpty()) {
                val ref = databaseReference.child(userId)
                val listener = ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(snapshotError: DatabaseError) {
                        cancel(snapshotError.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val products: MutableList<ProductEntity> = mutableListOf()
                        val children = snapshot.children
                        children.forEach { product ->
                            product.getValue(ProductEntity::class.java)?.let { products.add(it) }
                        }
                        trySend(products)
                    }
                })
                awaitClose { ref.removeEventListener(listener) }
            } else
                trySend(emptyList())
        }
    }

    override fun observeById(id: Int): Flow<ProductEntity?> {
        return callbackFlow {
            if (userId.isNotEmpty()) {
                val ref = databaseReference.child(userId)
                val listener = ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(snapshotError: DatabaseError) {
                        cancel(snapshotError.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val children = snapshot.children
                        children.forEach {
                            val product = it.getValue(ProductEntity::class.java)
                            if (product?.id == id) {
                                trySend(product)
                            }
                        }
                    }
                })
                awaitClose { ref.removeEventListener(listener) }
            } else
                trySend(null)
        }
    }

    override suspend fun insert(products: List<ProductEntity>): List<Long> {
        val productIdList = mutableListOf<Long>()
        if (userId.isNotEmpty()) {
            products.forEach { product ->
                databaseReference.child(userId).child(product.id.toString()).setValue(product)
                productIdList.add(product.id.toLong())
            }
        }
        return productIdList
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
