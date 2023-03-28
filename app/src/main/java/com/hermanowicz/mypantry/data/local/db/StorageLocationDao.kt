package com.hermanowicz.mypantry.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hermanowicz.mypantry.data.local.model.StorageLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageLocationDao {
    @Query("SELECT * FROM storage_locations WHERE id = (:id)")
    fun getStorageLocation(id: Int): StorageLocationEntity?

    @get:Query("SELECT * FROM storage_locations")
    val allStorageLocations: Flow<List<StorageLocationEntity>>

    @Insert
    fun insert(vararg storageLocations: StorageLocationEntity)

    @Delete
    fun delete(vararg storageLocations: StorageLocationEntity)

    @Update
    fun update(vararg storageLocations: StorageLocationEntity)

    @Query("DELETE FROM storage_locations")
    fun deleteAll()
}