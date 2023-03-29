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
    fun observeById(id: Int): Flow<StorageLocationEntity>

    @Query("SELECT * FROM storage_locations")
    fun observeAll(): Flow<List<StorageLocationEntity>>

    @Insert
    fun insert(storageLocation: StorageLocationEntity)

    @Delete
    fun delete(storageLocation: StorageLocationEntity)

    @Update
    fun update(storageLocation: StorageLocationEntity)

    @Query("DELETE FROM storage_locations")
    fun deleteAll()
}
