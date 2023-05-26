package com.hermanowicz.pantry.data.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.di.remote.dataSource.StorageLocationRemoteDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageLocationRemoteDataSourceImpl @Inject constructor() : StorageLocationRemoteDataSource {

    private val databaseReference =
        FirebaseDatabase.getInstance().reference.child("storage_locations")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun observeAll(): Flow<List<StorageLocationEntity>> {
        return callbackFlow {
            if (userId.isNotEmpty()) {
                val ref = databaseReference.child(userId)
                val listener = ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(snapshotError: DatabaseError) {
                        cancel(snapshotError.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val storageLocations: MutableList<StorageLocationEntity> = mutableListOf()
                        val children = snapshot.children
                        children.forEach { storageLocation ->
                            storageLocation.getValue(StorageLocationEntity::class.java)
                                ?.let { storageLocations.add(it) }
                        }
                        trySend(storageLocations)
                    }
                })
                awaitClose { ref.removeEventListener(listener) }
            } else {
                trySend(emptyList())
                awaitClose { }
            }
        }
    }

    override fun observeById(id: Int): Flow<StorageLocationEntity?> {
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
                            val storageLocation = it.getValue(StorageLocationEntity::class.java)
                            if (storageLocation?.id == id) {
                                trySend(storageLocation)
                            }
                        }
                    }
                })
                awaitClose { ref.removeEventListener(listener) }
            } else {
                trySend(null)
                awaitClose { }
            }
        }
    }

    override suspend fun insert(storageLocation: StorageLocationEntity) {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).child(storageLocation.id.toString())
                .setValue(storageLocation)
        }
    }

    override suspend fun update(storageLocation: StorageLocationEntity) {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).child(storageLocation.id.toString())
                .setValue(storageLocation)
        }
    }

    override suspend fun delete(storageLocation: StorageLocationEntity) {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).child(storageLocation.id.toString()).removeValue()
        }
    }

    override suspend fun deleteAll() {
        if (userId.isNotEmpty()) {
            databaseReference.child(userId).removeValue()
        }
    }
}
