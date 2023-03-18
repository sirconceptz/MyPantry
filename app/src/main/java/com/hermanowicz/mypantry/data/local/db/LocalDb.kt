package com.hermanowicz.mypantry.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hermanowicz.mypantry.data.local.model.ProductEntity

const val LOCAL_DB_NAME = "MyPantry.db"

@Database(
    entities = [
        ProductEntity::class
    ],
    version = 1
)
abstract class LocalDb : RoomDatabase() {
    abstract fun productDao(): ProductDao
}