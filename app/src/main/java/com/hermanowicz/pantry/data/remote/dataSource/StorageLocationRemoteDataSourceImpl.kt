package com.hermanowicz.pantry.data.remote.dataSource

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.di.remote.dataSource.StorageLocationRemoteDataSource
import com.hermanowicz.pantry.utils.InternetMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val internetMonitor: InternetMonitor
) : StorageLocationRemoteDataSource {

    private val database = FirebaseDatabase.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override suspend fun observeAll(): Flow<List<StorageLocationEntity>> {
        if (userId.isNotEmpty()) {
            return try {
                val products = mutableListOf<StorageLocationEntity>()
                val children = database
                    .reference
                    .child("categories")
                    .child(userId).get().await().children
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

    override suspend fun insert(products: List<StorageLocationEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(products: List<StorageLocationEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(products: List<StorageLocationEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        database
            .reference
            .child("storage_locations")
            .child(userId)
            .removeValue()
    }
}
