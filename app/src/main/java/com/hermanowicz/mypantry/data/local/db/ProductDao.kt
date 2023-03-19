package com.hermanowicz.mypantry.data.local.db

import androidx.room.*
import com.hermanowicz.mypantry.data.local.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product WHERE id = (:id)")
    fun observeById(id: Int): Flow<ProductEntity>

    @Query("SELECT * FROM Product ORDER BY expirationDate ASC")
    fun observeAll(): Flow<List<ProductEntity>>

    @Insert
    fun insert(products: List<ProductEntity>)

    @Delete
    fun delete(products: List<ProductEntity>)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Update
    fun update(products: List<ProductEntity>)
}