package com.hermanowicz.pantry.data.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.di.remote.dataSource.CategoryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRemoteDataSourceImpl @Inject constructor() : CategoryRemoteDataSource {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("categories")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override suspend fun observeAll(): Flow<List<CategoryEntity>> {
        if (userId.isNotEmpty()) {
            return try {
                val products = mutableListOf<CategoryEntity>()
                val children = databaseReference.child(userId).get().await().children
                children.forEach {
                    val product = it.getValue(CategoryEntity::class.java)!!
                    products.add(product)
                }
                flowOf(products)
            } catch (exception: Exception) {
                flowOf(emptyList())
            }
        }
        return flowOf(emptyList())
    }

    override fun observeById(id: Int): Flow<CategoryEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(categories: List<CategoryEntity>) {
        if (userId.isNotEmpty()) {
            categories.forEach { category ->
                databaseReference.child(userId).child(category.id.toString()).setValue(category)
            }
        }
    }

    override suspend fun update(categories: List<CategoryEntity>) {
        if (userId.isNotEmpty()) {
            categories.forEach { category ->
                databaseReference.child(userId).child(category.id.toString()).setValue(category)
            }
        }
    }

    override suspend fun delete(categories: List<CategoryEntity>) {
        if (userId.isNotEmpty()) {
            categories.forEach { category ->
                databaseReference.child(userId).child(category.id.toString()).removeValue()
            }
        }
    }

    override suspend fun deleteAll() {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).removeValue()
        }
    }
}
