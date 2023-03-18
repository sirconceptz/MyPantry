package com.hermanowicz.mypantry.data.local.db

import androidx.room.*
import com.hermanowicz.mypantry.data.local.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product WHERE id = (:id)")
    fun getProduct(id: Int): ProductEntity

    @get:Query("SELECT * FROM product ORDER BY expirationDate ASC")
    val allProducts: Flow<List<ProductEntity>>

    @Insert
    fun insert(productList: List<ProductEntity>)

    @Delete
    fun delete(productList: List<ProductEntity>)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Update
    fun update(productList: List<ProductEntity>)

    @get:Query("SELECT * FROM product ORDER BY expirationDate ASC")
    val allProductsAsList: List<ProductEntity>
}