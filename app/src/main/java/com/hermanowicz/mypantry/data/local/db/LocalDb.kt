package com.hermanowicz.mypantry.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hermanowicz.mypantry.data.local.model.CategoriesEntity
import com.hermanowicz.mypantry.data.local.model.PhotoEntity
import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.data.local.model.StorageLocationEntity

const val LOCAL_DB_NAME = "MyPantry.db"

@Database(
    entities = [
        ProductEntity::class,
        CategoriesEntity::class,
        StorageLocationEntity::class,
        PhotoEntity::class
    ],
    version = 1
)
abstract class LocalDb : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun storageLocationDao(): StorageLocationDao
}
