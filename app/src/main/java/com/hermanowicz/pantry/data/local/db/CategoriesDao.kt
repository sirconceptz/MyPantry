package com.hermanowicz.pantry.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hermanowicz.pantry.data.local.model.CategoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories WHERE id = (:id)")
    fun observeById(id: Int): Flow<CategoriesEntity>

    @Query("SELECT * FROM categories")
    fun observeAll(): Flow<List<CategoriesEntity>>

    @Query("SELECT * FROM categories")
    fun getAll(): List<CategoriesEntity>

    @Insert
    fun insert(vararg categories: CategoriesEntity)

    @Delete
    fun delete(vararg categories: CategoriesEntity)

    @Update
    fun update(vararg categories: CategoriesEntity)

    @Query("DELETE FROM categories")
    fun deleteAll()
}
