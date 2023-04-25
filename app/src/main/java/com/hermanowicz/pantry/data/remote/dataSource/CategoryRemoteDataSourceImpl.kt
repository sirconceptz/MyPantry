package com.hermanowicz.pantry.data.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.di.remote.dataSource.CategoryRemoteDataSource
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRemoteDataSourceImpl @Inject constructor(
) : CategoryRemoteDataSource {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("categories")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun observeAll(): Flow<List<CategoryEntity>> {
        return callbackFlow {
            if (userId.isNotEmpty()) {
                val ref = databaseReference.child(userId)
                val listener = ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(snapshotError: DatabaseError) {
                        cancel(snapshotError.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val categories: MutableList<CategoryEntity> = mutableListOf()
                        val children = snapshot.children
                        children.forEach { category ->
                            category.getValue(CategoryEntity::class.java)
                                ?.let { categories.add(it) }
                        }
                        trySend(categories)
                    }
                })
                awaitClose { ref.removeEventListener(listener) }
            } else
                trySend(emptyList())
        }
    }

    override fun observeById(id: Int): Flow<CategoryEntity?> {
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
                            val category = it.getValue(CategoryEntity::class.java)
                            if (category?.id == id) {
                                trySend(category)
                            }
                        }
                    }
                })
                awaitClose { ref.removeEventListener(listener) }
            } else
                trySend(null)
        }
    }

    override suspend fun insert(category: CategoryEntity) {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).child(category.id.toString()).setValue(category)
        }
    }

    override suspend fun update(category: CategoryEntity) {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).child(category.id.toString()).setValue(category)
        }
    }

    override suspend fun delete(category: CategoryEntity) {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).child(category.id.toString()).removeValue()
        }
    }

    override suspend fun deleteAll() {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).removeValue()
        }
    }
}
