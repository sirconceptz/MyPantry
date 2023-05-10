package com.hermanowicz.pantry.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hermanowicz.pantry.data.local.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE id = (:id)")
    fun observeById(id: Int): Flow<ProductEntity>

    @Query("SELECT * FROM products ORDER BY expirationDate ASC")
    fun observeAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products ORDER BY expirationDate ASC")
    fun getAll(): List<ProductEntity>

    @Insert
    fun insert(products: List<ProductEntity>): List<Long>

    @Query("DELETE FROM products WHERE id = (:id)")
    fun delete(id: Int)

    @Query("DELETE FROM products")
    fun deleteAll()

    @Update
    fun update(products: List<ProductEntity>)
}
