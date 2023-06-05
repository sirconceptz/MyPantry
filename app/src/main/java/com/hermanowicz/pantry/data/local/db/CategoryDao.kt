package com.hermanowicz.pantry.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE id = (:id)")
    fun observeById(id: Int): Flow<CategoryEntity>

    @Query("SELECT * FROM categories")
    fun observeAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories")
    fun getAll(): List<CategoryEntity>

    @Insert
    suspend fun insert(vararg categories: CategoryEntity)

    @Delete
    suspend fun delete(vararg categories: CategoryEntity)

    @Update
    suspend fun update(vararg categories: CategoryEntity)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}
