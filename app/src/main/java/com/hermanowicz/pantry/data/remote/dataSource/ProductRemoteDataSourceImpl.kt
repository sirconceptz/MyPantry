package com.hermanowicz.pantry.data.remote.dataSource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.di.remote.dataSource.ProductRemoteDataSource
import com.hermanowicz.pantry.utils.InternetMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val internetMonitor: InternetMonitor
) : ProductRemoteDataSource {

    private var database: FirebaseDatabase
    private var userId = ""

    init {
        FirebaseApp.initializeApp(context)
        database = FirebaseDatabase.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    override suspend fun observeAll(): Flow<List<ProductEntity>> {
        if (userId.isNotEmpty()) {
            return try {
                val products = mutableListOf<ProductEntity>()
                val children = database
                    .reference
                    .child("products")
                    .child(userId).get().await().children
                children.forEach {
                    val product = it.getValue(ProductEntity::class.java)!!
                    products.add(product)
                }
                flowOf(products)
            } catch (exception: Exception) {
                flowOf()
            }
        }
        return flowOf()
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
        database
//            .reference
//            .child("products")
//              .child(userId)
    }
}
