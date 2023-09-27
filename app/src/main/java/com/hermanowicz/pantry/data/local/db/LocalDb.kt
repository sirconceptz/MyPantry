package com.hermanowicz.pantry.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.data.local.model.ErrorEntity
import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        StorageLocationEntity::class,
        ErrorEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDb : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun storageLocationDao(): StorageLocationDao
    abstract fun categoriesDao(): CategoryDao
    abstract fun errorDao(): ErrorDao
}
