package com.hermanowicz.pantry.data.remote.dataSource

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.di.remote.dataSource.StorageLocationRemoteDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationRemoteDataSourceImpl @Inject constructor() : StorageLocationRemoteDataSource {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("categories")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override suspend fun observeAll(): Flow<List<StorageLocationEntity>> {
        if (userId.isNotEmpty()) {
            return try {
                val products = mutableListOf<StorageLocationEntity>()
                val children = databaseReference.child(userId).get().await().children
                children.forEach {
                    val product = it.getValue(StorageLocationEntity::class.java)!!
                    products.add(product)
                }
                flowOf(products)
            } catch (exception: Exception) {
                flowOf(emptyList())
            }
        }
        return flowOf(emptyList())
    }

    override fun observeById(id: Int): Flow<StorageLocationEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(storageLocations: List<StorageLocationEntity>) {
        if (userId.isNotEmpty()) {
            storageLocations.forEach { storageLocation ->
                databaseReference.child(userId).child(storageLocation.id.toString())
                    .setValue(storageLocation)
            }
        }
    }

    override suspend fun update(storageLocations: List<StorageLocationEntity>) {
        if (userId.isNotEmpty()) {
            storageLocations.forEach { storageLocation ->
                databaseReference.child(userId).child(storageLocation.id.toString())
                    .setValue(storageLocation)
            }
        }
    }

    override suspend fun delete(storageLocations: List<StorageLocationEntity>) {
        if (userId.isNotEmpty()) {
            storageLocations.forEach { storageLocation ->
                databaseReference.child(userId).child(storageLocation.id.toString()).removeValue()
            }
        }
    }

    override suspend fun deleteAll() {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).removeValue()
        }
    }
}
