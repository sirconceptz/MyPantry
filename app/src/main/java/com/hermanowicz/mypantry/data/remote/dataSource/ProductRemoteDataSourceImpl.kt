package com.hermanowicz.mypantry.data.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.remote.dataSource.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSourceImpl : ProductRemoteDataSource {

    private val database = Firebase.database
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun observeAll(): Flow<List<ProductEntity>> {
        if (userId.isNotEmpty()) {
//        database
//            .reference
//            .child("products")
//             .child(userId)
//            .get()
//            .addOnCompleteListener { products ->
//                if (products.isSuccessful) {
//                    //todo: implement
//                }
//            }
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
