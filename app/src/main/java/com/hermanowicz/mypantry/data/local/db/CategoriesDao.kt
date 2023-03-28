package com.hermanowicz.mypantry.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hermanowicz.mypantry.data.local.model.CategoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories WHERE id = (:id)")
    fun getCategory(id: Int): CategoriesEntity?

    @get:Query("SELECT * FROM categories")
    val allOwnCategories: Flow<List<CategoriesEntity>>

    @Insert
    fun insert(vararg categories: CategoriesEntity)

    @Delete
    fun delete(vararg categories: CategoriesEntity)

    @Update
    fun update(vararg categories: CategoriesEntity)

    @Query("DELETE FROM categories")
    fun deleteAll()
}
